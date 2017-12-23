package dbService;

import dbService.dao.PageDAO;
import dbService.dao.PersonDAO;
import dbService.dao.SiteDAO;
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
        int id = -1;
        try {
            openSessionAndTransation();
            PersonDAO personDAO = new PersonDAO(session);
            Person person = personDAO.getPersonByName(name);
            if(!(person==null))
            {
                System.out.println("Такой Person уже есть в БД!");
                closeSessionAndTransation();
                return id;
            }
            id = personDAO.insertPerson(name);
            closeSessionAndTransation();
        } catch (HibernateException e) {
            System.err.println("!!!HIBERNATE ERROR APPEARED!!!");
        } catch (NullPointerException e) {
            System.err.println("something wrong");
        }
        finally {
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
            if(person == null) System.out.println("Такого Person не найдено!");
            return person;
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



    /**
     * добавление нового Site в БД
     * @param name
     * @return id добавленного Site или -1 если операция не удалась
     */
    public int addSite(String name) {
        int id = -1;
        try {
            openSessionAndTransation();
            SiteDAO siteDAO = new SiteDAO(session);
            id = siteDAO.insertSite(name);
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
