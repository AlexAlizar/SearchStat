package dbService.dao;

import dbService.dataSets.Keyword;
import dbService.dataSets.Person;
import org.hibernate.Session;

/**
 * Created by User on 24.12.2017.
 */
public class KeywordDAO {

    private Session session;

    public KeywordDAO(Session session) {
        this.session = session;
    }

    public int insertKeyword(Person person, String name) {
        return (Integer) session.save(new Keyword(person, name));
    }

    public Keyword getKeywordById(int id) {
        Keyword keyword = (Keyword) session.get(Keyword.class, id);
        return keyword;
    }
}
