package kraulerService;

import dbService.DBService;
import dbService.HibernateUtil;
import dbService.dataSets.Page;
import kraulerService.parsingService.PageParser;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by User on 14.01.2018.
 */
public class AddLinksThread extends Thread {
    Page page;
    private DBService dbService;
    private SessionFactory sessionFactory;

    public AddLinksThread(Page page) {
        this.page = page;
        this.sessionFactory = HibernateUtil.getSessionFactory();

        this.dbService = new DBService(sessionFactory);
    }

    @Override
    public void run() {
        System.out.println("            t1:"+page.getId()+" - ADD LINKS PROCESS STARTED");

        addPageLinksFromSitemap();
  //      sessionFactory.close();

        System.out.println("            t1:"+page.getId()+" - ADD LINKS PROCESS FINISHED");
    }

    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     */
    private void addPageLinksFromSitemap() {
        System.out.println("            t1:"+page.getId()+" - SEARCH LINKS FOR SITEMAP");
        List<String> allLinksFromSitemap = PageParser.getLinkPagesFromSiteMap(page.getUrl());
        System.out.println("            t1:"+page.getId()+" - FOUND "+allLinksFromSitemap.size());
        dbService.updatePageDate(page);
        Date todayDate = new Date();
        for (String link : allLinksFromSitemap) {
            dbService.addPage(link, page.getSite(), todayDate, null, "link");
        }
    }
}
