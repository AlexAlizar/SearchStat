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
        SQLQuery query = session.createSQLQuery("INSERT into PersonPageRank(personID, pageID, rank) VALUES ("+person.getId()+" , "+page.getId()+", "+rank+")");
        return query.executeUpdate();

    }

    public PersonPageRank getPersonPageRank(Person person, Page page) {
        SQLQuery query = session.createSQLQuery("SELECT * FROM personPageRank where personID="+person.getId()+" AND pageID="+page.getId());
        query.addEntity(PersonPageRank.class);
        List<PersonPageRank> resultList = query.list();
        if (resultList.size()>0) {
            PersonPageRank result = resultList.get(0);
            return result;
        }
        else return null;
    }

    public void updateRank(Person person, Page page, int rank) {
        SQLQuery query = session.createSQLQuery("UPDATE personPageRank set rank="+rank+" where personID="+person.getId()+" AND pageID="+page.getId());
        query.executeUpdate();
    }
}
