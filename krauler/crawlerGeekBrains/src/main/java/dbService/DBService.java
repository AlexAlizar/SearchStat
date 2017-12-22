package dbService;

import dbService.dao.PageDAO;
import dbService.dao.PersonDAO;
import dbService.dataSets.Person;
import dbService.dataSets.Site;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;

public class DBService {

    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public DBService(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

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
            openSessionAndTransation();
            PersonDAO personDAO = new PersonDAO(session);
            id = personDAO.insertPerson(name);
            closeSessionAndTransation();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } finally {
            return id;
        }
    }

    /**
     * получение Person из БД по id
     * @param id
     * @return найденный экземпляр Person
     */
    public Person getPersonById(int id) {
        Person person = null;
        try {
            openSessionAndTransation();
            PersonDAO personDAO = new PersonDAO(session);
            person = personDAO.getPersonById(id);
            closeSessionAndTransation();
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

    /**
     * получение Person из БД по name
     * @param name
     * @return найденный экземпляр Person
     * @warning если в БД два одинаковых name то падает...
     * @warning надо обсепчечить уникальность name в таблице Persons
     */
    public Person getPersonByName(String name) {
        Person person = null;
        try {
            openSessionAndTransation();
            PersonDAO personDAO = new PersonDAO(session);
            person = personDAO.getPersonByName(name);
            closeSessionAndTransation();
        } catch (HibernateException e) {
            System.err.println("Попытка найти по имени Персону, которых в БД более одной(например два Vasya и его ищем => ошибка)");
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

    /**
     * добавление новой Page в БД
     * @param url
     * @param site
     * @param foundDateTime
     * @param lastScanDate
     * @return id добавленной Page или -1 если операция не удалась
     */
    public int addPage(String url, Site site, Date foundDateTime, Date lastScanDate){
        int id = -1;
        try {
            openSessionAndTransation();
            PageDAO pageDAO = new PageDAO(session);
            id = pageDAO.insertPage(url, site, foundDateTime, lastScanDate);
            closeSessionAndTransation();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } finally {
            return id;
        }
    }






    private void openSessionAndTransation() {
        this.session = this.sessionFactory.openSession();
        this.transaction = session.beginTransaction();
    }

    private void closeSessionAndTransation() {
        this.transaction.commit();
        this.session.close();
        this.transaction = null;
        this.session = null;
    }

}
