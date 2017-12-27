package ru.geekbrains.krawler;

import dbService.DBService;
import dbService.dataSets.Site;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program");
        System.out.println("Проверка связи =)");
        //пример вызова метода download
    //    System.out.println(Downloader.download("lenta.ru"));

//        System.out.println("Проверка от Алексей Грунтов =)");

        /**
         * чтобы всё работало, надо:
         * 1.1) создать connection в MySQL
         * 1.2) проврить чтобы пароль от созданного connection совпадал с тем под которым вы входите туда
         * 2) создать schema "test"
         * 3) если программа запускается в первый раз, то надо поменять параметр hbm2ddl.auto на create; в последующие запуски надо чтобы этот параметр был равен update
         * 4) ВУАЛЯ - можно использовать методы add/insert Person
         */

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        DBService dbService = new DBService(sessionFactory);
        //алгоритм работы краулера, пошагово
        // 1)
        // 1.1) обход таблицы Sites с поиском тех сайтов, которых нету в табоце Pages
        List<Site> sitesWithoutPages = dbService.gettAllSiteWithoutPage();
        // 1.2) Добавление страниц с LastScanDate = null в таблицу Pages для найденных Site
        Date todayDate = new Date();
        for (Site site: sitesWithoutPages) {
            dbService.addPage("http://"+site.getName()+"/robots.txt", site, todayDate, null);
        }
        // 2) Обход ссылок, которых по которым раньше не производился обход
        // 2.1) Поиск таких ссылков
//        List<Page> pagesWithoutScan = dbService.gettNonScannedPages();
//        for(Page page: pagesWithoutScan) {
//            if(page.getUrl().contains("robots.txt")) {
//
//            };
//        }


        // примеры использования методов

//         добавление сайтов

//          dbService.addSite("lenta.ru");
//          dbService.addSite("bfm.ru");
//          dbService.addSite("infox.ru");

        // добавление персон

//          dbService.addPerson("Путин");
//          dbService.addPerson("Медведев");
//          dbService.addPerson("Навальный");

        // добавление страниц
//        Site site = dbService.getSiteByName("lenta.ru");
//        Date date1 = new Date();
//        Date date2 = new Date(date1.getTime() + 86400000); //+1 сутки в миллисекундах
//        dbService.addPage("lenta.ru/testurl.html", site, date1, date2);


        // получение списка сайтов, у которых нет соответствия в pages
//        List<Site> sites = null;
//        sites = dbService.gettAllSiteWithoutPage();
//
//        for (Site site : sites ) {
//            System.out.println(site.toString());
//
//        }

        // добавление ключевых слов
//        Person person = dbService.getPersonByName("Путин");
//        dbService.addKeyword(person,"Путина");
//        dbService.addKeyword(person,"Путину");

        // получение ключевых слов
//        List<Keyword> keywords = null;
        //Person person = dbService.getPersonByName("Путин");
//        keywords = dbService.getKeywordByPerson(person);
//
//        for ( Keyword kwd : keywords ) {
//            System.out.println(kwd.toString());
//        }

        // вставка/обновление рейтинга

//        Person person = dbService.getPersonByName("Путин");
//        Page page = dbService.getPageById(1);
//        dbService.writeRank(person, page, 200);


        // получение списка неотсканенных страниц

//                List<Page> pages = null;
//                pages = dbService.gettNonScannedPages();
//
//               for ( Page pg : pages ) {
//                   System.out.println(pg.toString());
//               }

        sessionFactory.close();

        //Site site = dbService.getSiteByName("somesite");
        //Date date1 = new Date();
        //Date date2 = new Date(date1.getTime() + 86400000); //+1 сутки в миллисекундах
        //dbService.addPage("somesite2/testurl.com", site, date1, date2);
        //System.out.println(dbService.getPageById(1));
        //  System.out.println(dbService.getPageById(22));


//            System.out.println(dbService.getPersonById(2).toString());
//            System.out.println(dbService.getPersonByName("Petya").toString());
//            System.out.println(dbService.getPersonByName("Petya").toString());
//            System.out.println(dbService.getPersonByName("Vasya").toString());
//            System.out.println(dbService.getPersonByName("Vas").toString());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

    }
}
