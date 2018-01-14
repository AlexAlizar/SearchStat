package dbService.dao;

import dbService.dataSets.Page;
import dbService.dataSets.Site;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PageDAO {

    private Session session;

    public PageDAO(Session session) {
        this.session = session;
    };

    public int insertPage(String url, Site site, Date foundDateTime, Date lastScanDate) {
        Page page = getPageByUrl(url);
        if (page == null) {
            return (Integer) session.save(new Page(url, site, foundDateTime, lastScanDate));
        }
        return page.getId();
    }

    public void updatePageDate(Page page) {
        page.setLastScanDate(new Date());
        session.update(page);
    }

    public Page getPageById(int id) {
        Page page = (Page) session.get(Page.class, id);
        return page;
    }

    public Page getPageByUrl(String url) {
        Page page = (Page) session.get(Page.class, url);
        return page;
    }


    public List<Page> getNonScannedPages() {

        SQLQuery query = session.createSQLQuery("SELECT * FROM PAGES WHERE LastScanDate is null");
        query.addEntity(Page.class);
        return query.list();
    }

    public void resetOldSiteMap(Date currentDate, String type) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        String txtSQL = "UPDATE pages set LastScanDate = null where LastScanDate<>'"+sdfDate.format(currentDate)+"'";
        if (type != "all") {
            txtSQL+= " AND url LIKE '%"+type+"%'";
        }
        SQLQuery query = session.createSQLQuery(txtSQL);

        query.executeUpdate();
    }


}
