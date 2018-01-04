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
public class Krauler {

    private DBService dbService;
    private SessionFactory sessionFactory;

    public Krauler()
    {
        this.sessionFactory = HibernateUtil.getSessionFactory();

        this.dbService = new DBService(sessionFactory);
    }

    public void Work() {

        addSitePagesWithoutScan();

        addSiteMapPageFromRobots(dbService.getNonScannedPages());

        addPageLinksFromSitemap(dbService.getNonScannedPages());

        addRanksForPersons(dbService.getNonScannedPages());

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
            dbService.addPage("http://" + site.getName()+"/robots.txt", site, todayDate, null);
        }
    }

    /**
     * метод, который для каждой строки вида <имя сайта/robots.txt> в таблице Pages
     * добавляет в таблицу Pages ссылки на найденные в robots.txt sitemap-ы
     * @param pages
     */
    private void addSiteMapPageFromRobots(List<Page> pages) {
        for(Page page: pages) {
            String url = page.getUrl();
            dbService.updatePageDate(page);
            if(url.contains("robots.txt"))
            {
                List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(url));
                for (String link: foundedSitemapLinks) {
                    dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
                }
            }
        }
    }

    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     * @param pages
     */
    private void addPageLinksFromSitemap(List<Page> pages) {
        for(Page page: pages) {
            String url = page.getUrl();
            dbService.updatePageDate(page);
            if(url.contains("sitemap"))
            {
                List<String> allLinksFromSitemap = PageParser.getLinkPagesFromSiteMap(url);
                for(String link: allLinksFromSitemap) {
                    dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
                }
            }
        }
    }

    /**
     * метод, который считает Rank для каждой персоны считает количесвто упоминаний на каждой странице из передаваемого pages
     * @param pages
     */
    private void addRanksForPersons(List<Page> pages) {
        List<Person> persons = dbService.getAllPerson();

        for(Page page: pages)
        {
            String url = page.getUrl();
            dbService.updatePageDate(page);
            for (Person person: persons) {
                int rank = 0;
                List<Keyword> keywordList = dbService.getKeywordByPerson(person);
                String HTMLString = Downloader.download(url);
                for(int i = 0; i < keywordList.size(); i++)
                {
                    String keyword = keywordList.get(i).getName();
                    rank+=PageParser.parsePage(HTMLString, keyword);
                }
                dbService.writeRank(
                        person,
                        page,
                        rank
                );
            }
        }
    }

}