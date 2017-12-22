package dbService;

import dbService.dao.PersonDAO;
import dbService.dataSets.Person;
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

    //TODO: вынести операции
    //TODO: Session session = sessionFactory.openSession();
    //TODO: Transaction transaction = session.beginTransaction();
    //TODO: а также
    //TODO: transaction.commit();
    //TODO: session.close();
    //TODO: из методов add/insert+имя_класса
    //TODO: в отдельные методы ОТКРЫТЬ() и ЗАКРЫТЬ() или что-то вроде того

    /**
     * добавление новой Персоны в БазуДанных
     * @param name - имя персоны
     * @return id добавленной персоны или -1 если операция не удалась
     */
    public int addPerson(String name){
        //TODO: сделать так, чтобы в БД мог добавляться только один пользователь с уникальным именем
        //TODO: иначе ошибка при вызове getPersonByName() - если в БД лежит несколько персон с одинаковыми именами
        int id = -1;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PersonDAO personDAO = new PersonDAO(session);
            id = personDAO.insertPerson(name);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } finally {
            return id;
        }
    }

    public Person getPersonById(int id) {
        Person person = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PersonDAO personDAO = new PersonDAO(session);
            person = personDAO.getPersonById(id);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } finally {
            if(!person.equals(null))
            {
                return person;
            }
            else
            {
                throw new NullPointerException("Person  not found(null reference)");
            }
        }
    }

    public Person getPersonByName(String name) {
        Person person = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PersonDAO personDAO = new PersonDAO(session);
            person = personDAO.getPersonByName(name);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("Попытка найти по имени Персону, которых в БД более одной(например два Vasya и его ищем => ошибка)");
        } finally {
            if(!person.equals(null))
            {
                return person;
            }
            else
            {
                System.out.println("Person  not found(null reference)");
                throw new NullPointerException();
            }
        }
    }

}
