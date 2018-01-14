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

import java.util.Date;
import java.util.List;

/**
 * Created by alloyer on 02.01.2018.
 */



public class Krauler extends Thread {

    final int dayDuration = 1000*5; //длительность дня в миллисекундах
    final int durationOfWork = 3;          //длительность работы краулера в днях

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

        int days = 0;
        addSitePagesWithoutScan();
        List<Page> pages;

        while (days <= durationOfWork) {   // краулер работает указанное количество дней
            pages = dbService.getNonScannedPages();
            int count = pages.size();
            while (count > 0) {
                for (Page page : pages) {
                    String url = page.getUrl();
                    if (url.contains("robots.txt")) {
                        addSiteMapPageFromRobots(page);
                    } else if (url.contains("sitemap")) {
                        addPageLinksFromSitemap(page);
                    } else addRanksForPersons(page);
                }
                pages = dbService.getNonScannedPages();
                count = pages.size();
            }

            try {
                Thread.sleep(dayDuration);           // засыпаем на сутки
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateLinks("sitemap");
            days+=1;
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
            dbService.addPage(site.getName()+"/robots.txt", site, todayDate, null);
        }
    }

    /**
     * метод,
     * добавляет в таблицу Pages ссылки на найденные в robots.txt sitemap-ы
     * @param page
     */
    private void addSiteMapPageFromRobots(Page page) {
        List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(page.getUrl()));
        dbService.updatePageDate(page);
        for (String link : foundedSitemapLinks) {
            dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
        }
    }


    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     * @param page
     */
    private void addPageLinksFromSitemap(Page page) {
        List<String> allLinksFromSitemap = PageParser.getLinkPagesFromSiteMap(page.getUrl());
        dbService.updatePageDate(page);
        for (String link : allLinksFromSitemap) {
            dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
        }
    }

    /**
     * метод, который считает Rank для каждой персоны считает количесвто упоминаний на переданной странице
     * @param page
     */
    private void addRanksForPersons(Page page) {
        List<Person> persons = dbService.getAllPerson();

        for (Person person : persons) {
            int rank = 0;
            List<Keyword> keywordList = dbService.getKeywordByPerson(person);
            String HTMLString = Downloader.download(page.getUrl());
            for (int i = 0; i < keywordList.size(); i++) {
                String keyword = keywordList.get(i).getName();
                rank += PageParser.parsePage(HTMLString, keyword);
            }
            dbService.writeRank(
                    person,
                    page,
                    rank
            );
        }
        dbService.updatePageDate(page);
    }

    /**
     * метод, сбрасывает ссылки по сайтмапам которые старше текущего дня
     * @param type = тип урлов для апдейта all/sitemap
     */
    private void updateLinks(String type) {
        dbService.resetOldPages(type);
    }
}