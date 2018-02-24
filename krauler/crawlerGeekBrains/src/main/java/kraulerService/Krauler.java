package kraulerService;

import dbService.DBService;
import dbService.HibernateUtil;
import dbService.dataSets.Keyword;
import dbService.dataSets.Page;
import dbService.dataSets.Person;
import dbService.dataSets.Site;
import kraulerService.parsingService.Downloader;
import kraulerService.parsingService.LogWork;
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

    public Krauler() {
        this.sessionFactory = HibernateUtil.getSessionFactory();

        this.dbService = new DBService(sessionFactory);
    }

    @Override
    public void run() {
        Work();
    }

    public void Work() {

        AddLinksThread t2 = null;
        LogWork.logWrite("Start program");
        LogWork.logWrite("t0: Started", 1);
        addSitePagesWithoutScan();
        List<Page> pages;
        LogWork.logWrite("t0: Update links began", 1);
        updateLinks("sitemap");
        updateLinks("direct");
        LogWork.logWrite("t0: Update links complete", 1);
        LogWork.logWrite("t0: Search pages for analyze...", 1);
        pages = dbService.getNonScannedPages();

        int count = pages.size();
        LogWork.logWrite("t0: Found " + count + " pages", 1);
        while (count > 0) {
            for (Page page : pages) {
                String url = page.getUrl();
                if (page.getType_page().equals("robots")) {
                    addSiteMapPageFromRobots(page);
                } else if (page.getType_page().equals("sitemap") || page.getType_page().equals("direct")) {
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

            // и еще раз вытаскиваем список неотсканированных страниц
            LogWork.logWrite("t0: Search pages for analyze...", 1);
            pages = dbService.getNonScannedPages();

            if (pages != null) {
                count = pages.size();
                LogWork.logWrite("t0: Found " + count + " pages", 1);
            } else count = 0;
        }

        try {
            if (t2 != null) {
                LogWork.logWrite("t0: Waiting while t1 been complete.", 1);
                t2.join();     // ждем когда закончится вытягивание ссылок с сайтмапа
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogWork.logWrite("Complete all threads successful!", 1);
        sessionFactory.close();
    }

    /**
     * метод, который идёт по таблице Sites и для каждого сайта, для которого нету записей в таблице Pages
     * добавляет в таблицу Pages строку <имя сайта/robots.txt>
     */
    private void addSitePagesWithoutScan() {
        List<Site> sitesWithoutPages = dbService.gettAllSiteWithoutPage();
        Date todayDate = new Date();
        for (Site site : sitesWithoutPages) {
            dbService.addPage(site.getName() + "/robots.txt", site, todayDate, null, "robots");
        }
    }

    /**
     * метод,
     * добавляет в таблицу Pages ссылки на найденные в robots.txt sitemap-ы
     *
     * @param page
     */
    public void addSiteMapPageFromRobots(Page page) {
        List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(page.getUrl()));
        dbService.updatePageDate(page);
        Date todayDate = new Date();
        if (foundedSitemapLinks.size() != 0) { //сайтмапы найдены
            for (String link : foundedSitemapLinks) {
                dbService.addPage(link, page.getSite(), todayDate, null, "sitemap");
            }
        } else { //сайтмапы не найдены
            dbService.addPage("https://"+page.getSite().getName(), page.getSite(), todayDate, null, "direct");
        }
    }


    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     *
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


    private void addRanksForPersons(Page page) {
        Date StartDate = new Date();
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

        String HTMLString = Downloader.download(page.getUrl());

        if (HTMLString.equals("Page not found!")) {
            dbService.updatePageDateAndType(page, "deleted");
        } else {


            ranks = PageParser.parsePageSuperPuper(HTMLString, keywords);

            // преобразовываем массив по кейвордам в массив по персонам
            for (int i = 0; i < ranks.length; i++) {
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
            Date EndDate = new Date();

            LogWork.logWrite("t0: Calculation ranks for " + page.getId() + " complete in " + (EndDate.getTime() - StartDate.getTime()) / 1000 + " sec.", 3);
        }
    }

    /**
     * метод, сбрасывает ссылки по сайтмапам которые старше текущего дня
     *
     * @param type = тип урлов для апдейта all/sitemap
     */
    private void updateLinks(String type) {
        dbService.resetOldPages(type);
    }
}