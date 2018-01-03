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

        addPagesWithoutScan();

        List<Page> pagesWithoutScan = dbService.gettNonScannedPages();

        for(Page page: pagesWithoutScan) {
            String url = page.getUrl();
            dbService.updatePageDate(page);
            if(url.contains("robots.txt"))
            {
                addSiteMapPageFromRobots(url, page);
            }
            else if(url.contains("sitemap"))
            {
                addLinksToPagesFromSitemap(url, page);
            }
            else
            {
                addRanksForPersons(url, page);
            }
        }

        sessionFactory.close();
    }

    private void addPagesWithoutScan() {
        List<Site> sitesWithoutPages = dbService.gettAllSiteWithoutPage();
        Date todayDate = new Date();
        for (Site site: sitesWithoutPages) {
            dbService.addPage("http://"+site.getName()+"/robots.txt", site, todayDate, null);
        }
    }

    private void addSiteMapPageFromRobots(String url, Page page) {
        List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(url));
        for (String link: foundedSitemapLinks) {
            dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
        }
    }

    private void addLinksToPagesFromSitemap(String url, Page page) {
        List<String> foundedLinksOfSite = PageParser.parseSiteMap(url);
        for(String link: foundedLinksOfSite) {
            dbService.addPage(link, page.getSite(), page.getFoundDateTime(), null);
        }
    }

    private void addRanksForPersons(String url, Page page) {
        List<Person> persons = dbService.getAllPerson();

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