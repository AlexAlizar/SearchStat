package kraulerService;

import dbService.DBService;
import dbService.HibernateUtil;
import dbService.dataSets.Keyword;
import dbService.dataSets.Page;
import dbService.dataSets.Person;
import dbService.dataSets.Site;
import kraulerService.parsingService.Downloader;
import kraulerService.parsingService.PageParser;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alloyer on 02.01.2018.
 */



public class Krauler extends Thread {

    private DBService dbService;
    private SessionFactory sessionFactory;

    public Krauler()
    {
        this.sessionFactory = HibernateUtil.getSessionFactory();

        this.dbService = new DBService(sessionFactory);
    }

    @Override
    public void run() {
        Work();
    }

    public void Work() {

        AddLinksThread t2 = null;

        addSitePagesWithoutScan();
        List<Page> pages;

            updateLinks("sitemap");
            pages = dbService.getNonScannedPages();
            int count = pages.size();
            while (count > 0) {
                for (Page page : pages) {
                    String url = page.getUrl();
                    if (count % 100 == 0) {
                        System.out.println("Analyze " + url);
                        System.out.println("Left links:" + count);
                    }
                    if (url.contains("robots.txt")) {
                        addSiteMapPageFromRobots(page);
                    } else if (url.contains("sitemap")) {
//                            addPageLinksFromSitemap(page);
//                      // запускаем добавление ссылок в параллельном потоке

                        if (t2 == null) {
                            t2 = new AddLinksThread(page);
                            t2.start();
                        } else if (t2 != null && t2.getState() == State.TERMINATED) {
                            t2 = new AddLinksThread(page);
                            t2.start();
                        }
                    } else addRanksForPersons(page);
                }

                // приостанавливаем на чуть-чуть
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // и еще раз вытаскиваем список неотсканированных страниц
                pages = dbService.getNonScannedPages();
                if (pages != null) {
                    count = pages.size();
                }
                else count = 0;
            }

            try {
                if (t2 != null) {
                    System.out.println("Waiting while t2 been complete.");
                    t2.join();     // ждем когда закончится вытягивание ссылок с сайтмапа
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        sessionFactory.close();
    }

    /**
     * метод, который идёт по таблице Sites и для каждого сайта, для которого нету записей в таблице Pages
     * добавляет в таблицу Pages строку <имя сайта/robots.txt>
     */
    private void addSitePagesWithoutScan() {
        List<Site> sitesWithoutPages = dbService.gettAllSiteWithoutPage();
        Date todayDate = new Date();
        for (Site site: sitesWithoutPages) {
            dbService.addPage(site.getName()+"/robots.txt", site, todayDate, null, "robots");
        }
    }

    /**
     * метод,
     * добавляет в таблицу Pages ссылки на найденные в robots.txt sitemap-ы
     * @param page
     */
    private void addSiteMapPageFromRobots(Page page) {
        List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(page.getUrl(),"UTF-8"));
        dbService.updatePageDate(page);
        Date todayDate = new Date();
        for (String link : foundedSitemapLinks) {
            dbService.addPage(link, page.getSite(), todayDate, null, "sitemap");
        }
    }


    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     * @param page
     */
    private void addPageLinksFromSitemap(Page page) {
        List<String> allLinksFromSitemap = PageParser.getLinkPagesFromSiteMap(page.getUrl());
        dbService.updatePageDate(page);
        Date todayDate = new Date();
        for (String link : allLinksFromSitemap) {
            dbService.addPage(link, page.getSite(), todayDate, null, "link");
        }
    }

    /**
     * метод, который считает Rank для каждой персоны считает количесвто упоминаний на переданной странице
     * @param page
     */
//    private void addRanksForPersons(Page page) {
//        System.out.println("Start old function:"+new Date().toString());
//        List<Person> persons = dbService.getAllPerson();
//
//        for (Person person : persons) {
//            int rank = 0;
//
//            List<Keyword> keywordList = dbService.getKeywordByPerson(person);
//            String HTMLString = Downloader.download(page.getUrl(),"UTF-8");
//            if (HTMLString.equals("windows-1251")) {
//                HTMLString = Downloader.download(page.getUrl(),"windows-1251");
//            }
//            for (int i = 0; i < keywordList.size(); i++) {
//                String keyword = keywordList.get(i).getName();
//                rank += PageParser.parsePage(HTMLString, keyword);
//            }
//            dbService.writeRank(
//                    person,
//                    page,
//                    rank
//            );
//        }
//
//        dbService.updatePageDate(page);
//        System.out.println("End old function:"+new Date().toString());
//        addRanksForPersonsNew(page);
//    }
    private void addRanksForPersons(Page page) {
        //System.out.println("Start new function:"+new Date().toString());
        List<Person> persons = dbService.getAllPerson();
        List<Keyword> keywordsObject = dbService.getAllKeywords();
        ArrayList<String> keywords = new ArrayList<>();
        int[] ranks;
        int[] ranksByPerson = new int[persons.size()];
        int index;
        Person person;

        for (Keyword keyword : keywordsObject) {
            keywords.add(keyword.getName().trim().toLowerCase());
        }

        String HTMLString = Downloader.download(page.getUrl(), "UTF-8");

        if (HTMLString.equals("windows-1251")) {
            HTMLString = Downloader.download(page.getUrl(), "windows-1251");
        }

        ranks = PageParser.parsePageSuper(HTMLString, keywords);

        // преобразовываем массив по кейвордам в массив по персонам
        for (int i = 0; i<ranks.length; i++) {
            // определяем индекс
            person = keywordsObject.get(i).getPerson();
            index = persons.indexOf(person);
            ranksByPerson[index] += ranks[i];
        }

        for (int i = 0; i < persons.size(); i++) {
            dbService.writeRank(
                    persons.get(i),
                    page,
                    ranksByPerson[i]);
        }
         dbService.updatePageDate(page);
        //System.out.println("End new function:"+new Date().toString());
    }

    /**
     * метод, сбрасывает ссылки по сайтмапам которые старше текущего дня
     * @param type = тип урлов для апдейта all/sitemap
     */
    private void updateLinks(String type) {
        dbService.resetOldPages(type);
    }
}