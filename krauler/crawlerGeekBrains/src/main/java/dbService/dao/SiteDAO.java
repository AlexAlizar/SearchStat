package dbService.dao;

import dbService.dataSets.Site;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by alloyer on 23.12.2017.
 */
public class SiteDAO {

    private Session session;

    public SiteDAO(Session session) {
        this.session = session;
    }

    public int insertSite(String name) {
        return (Integer) session.save(new Site(name));
    }

    public Site getSiteByName(String name) {
        Criteria criteria = session.createCriteria(Site.class);

        Site site = (Site) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        return site;
    }

    public List<Site> getAllSite() {
        Criteria criteria = session.createCriteria(Site.class);
        return criteria.list();
    }

    public List<Site> getAllSiteWithoutPage() {

        SQLQuery query = session.createSQLQuery("SELECT * FROM SITES WHERE ID NOT IN (SELECT DISTINCT SiteID FROM PAGES)");
        query.addEntity(Site.class);
        return query.list();
    }
}
