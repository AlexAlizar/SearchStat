package kraulerService;

import dbService.DBService;
import dbService.HibernateUtil;
import dbService.dataSets.Keyword;
import dbService.dataSets.Page;
import dbService.dataSets.Person;
import dbService.dataSets.Site;
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

            if(url.contains("robots.txt"))
            {
                addPageFromRobots(url, page);
            }
            else if(url.contains("sitemap"))
            {
                addLinksFromSitemap(url, page);
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

    private void addPageFromRobots(String url, Page page) {
        List<String> foundedSitemapLinks = PageParser.searchSiteMap(Downloader.download(url));
        for (String link: foundedSitemapLinks) {
            dbService.addPage(url, page.getSite(), page.getFoundDateTime(), new Date());
        }
    }

    private void addLinksFromSitemap(String url, Page page) {
        List<String> foundedLinksOfSite = PageParser.parseSiteMap(url);
        for(String link: foundedLinksOfSite) {
            dbService.addPage(link, page.getSite(), page.getFoundDateTime(), new Date());
        }
    }

    private void addRanksForPersons(String url, Page page) {
        List<Person> persons = dbService.getAllPerson();

        for (Person person: persons) {
            List<Keyword> keywordList = dbService.getKeywordByPerson(person);
            for(int i = 0; i < keywordList.size(); i++)
            {
                String HTMLString = Downloader.download(url);

                String keyword = keywordList.get(i).getName();

                dbService.writeRank(
                        person,
                        page,
                        PageParser.parsePage(HTMLString, keyword)
                );
            }
        }
    }

}