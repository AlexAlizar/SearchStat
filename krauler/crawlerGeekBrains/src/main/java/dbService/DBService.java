package dbService;

import dbService.dao.PersonDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class DBService {

    private final SessionFactory sessionFactory;

    public DBService(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    /*
    
     */
    public int addPerson(String name){
        int id = -1;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PersonDAO dao = new PersonDAO(session);
            id = dao.insertPerson(name);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } finally {
            return id;
        }
    }

}
