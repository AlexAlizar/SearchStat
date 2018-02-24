package kraulerService;

import dbService.DBService;
import dbService.HibernateUtil;
import dbService.dataSets.Page;
import kraulerService.parsingService.LogWork;
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
        LogWork.logWrite("            t1:"+page.getId()+" - ADD LINKS PROCESS STARTED", 1);

        addPageLinksFromPage();
  //      sessionFactory.close();

        LogWork.logWrite("            t1:"+page.getId()+" - ADD LINKS PROCESS FINISHED", 1);
    }

    /**
     * метод, который добавляет в Pages все найденные ссылки на web-страницы из sitemap-ов
     */
    private void addPageLinksFromPage() {
        List<String> allLinksFromSitemap;
        LogWork.logWrite("            t1:"+page.getId()+" - SEARCH LINKS FOR "+page.getType_page().toUpperCase(), 2);
        if (page.getType_page().equals("sitemap")) {
            allLinksFromSitemap = PageParser.getLinkPagesFromSiteMap(page.getUrl());
        }
        else
        {
            allLinksFromSitemap = PageParser.collectLinkOnPage(page);
        }
        LogWork.logWrite("            t1:"+page.getId()+" - FOUND "+allLinksFromSitemap.size(), 2);
        dbService.updatePageDate(page);
        Date todayDate = new Date();
        for (String link : allLinksFromSitemap) {
            dbService.addPage(link, page.getSite(), todayDate, null, "link");
        }
    }
}
