package dbService.dao;

import dbService.dataSets.Person;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class PersonDAO {

    private Session session;

    public PersonDAO(Session session) {
        this.session = session;
    };

    public int insertPerson(String name) throws HibernateException {
        return (Integer) session.save(new Person(name));
    }

    public Person getPersonById(int id) throws  HibernateException{
        Person person = (Person) session.get(Person.class, id);
        return person;
    }

    public Person getPersonByName(String name) throws HibernateException{
        Criteria criteria = session.createCriteria(Person.class);
        Person person = (Person) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        return person;
    }

}
