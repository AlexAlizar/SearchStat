package dbService.dao;

import dbService.dataSets.Site;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
}