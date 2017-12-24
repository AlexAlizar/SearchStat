package dbService.dao;

import dbService.dataSets.Page;
import dbService.dataSets.Site;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class PageDAO {

    private Session session;

    public PageDAO(Session session) {
        this.session = session;
    };

    public int insertPage(String url, Site site, Date foundDateTime, Date lastScanDate) {
        return (Integer) session.save(new Page(url, site, foundDateTime, lastScanDate));
    }

    public Page getPageById(int id) {
        Page page = (Page) session.get(Page.class, id);
        return page;
    }

    public List<Page> getNonScannedPages() {

        SQLQuery query = session.createSQLQuery("SELECT * FROM PAGES WHERE LastScanDate is null");
        query.addEntity(Page.class);
        return query.list();
    }


}
