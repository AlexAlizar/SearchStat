package dbService.dao;

import dbService.dataSets.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Created by alloyer on 22.12.2017.
 */
public class PersonDAO {

    private Session session;

    public PersonDAO(Session session) {
        this.session = session;
    };

    public int insertPerson(String name) throws HibernateException {
        return (Integer) session.save(new Person(name));
    }

}
