package dbService.dao;

import dbService.dataSets.Page;
import dbService.dataSets.Person;
import dbService.dataSets.PersonPageRank;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by User on 24.12.2017.
 */
public class PersonPageRankDAO {
    private Session session;

    public PersonPageRankDAO(Session session) {
        this.session = session;
    }

    public int insertRank(Person person, Page page, int rank) {
    //    return (Integer) session.save(new PersonPageRank(person, page, rank));
        SQLQuery query = session.createSQLQuery("INSERT into searchstat_crawler_testdrive.person_page_rank(person_id, page_id, rank) VALUES ("+person.getId()+" , "+page.getId()+", "+rank+")");
        return query.executeUpdate();

    }

    public PersonPageRank getPersonPageRank(Person person, Page page) {
        SQLQuery query = session.createSQLQuery("SELECT * FROM searchstat_crawler_testdrive.person_page_rank where person_id="+person.getId()+" AND page_id="+page.getId());
        query.addEntity(PersonPageRank.class);
        List<PersonPageRank> resultList = query.list();
        if (resultList.size()>0) {
            PersonPageRank result = resultList.get(0);
            return result;
        }
        else return null;
    }

    public void updateRank(Person person, Page page, int rank) {
        SQLQuery query = session.createSQLQuery("UPDATE searchstat_crawler_testdrive.person_page_rank set rank="+rank+" where person_id="+person.getId()+" AND page_id="+page.getId());
        query.executeUpdate();
    }
}
