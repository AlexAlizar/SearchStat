package ru.geekbrains.krawler;

import dbService.DBService;
import dbService.dataSets.Person;
import dbService.dataSets.Site;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Krawler {
    public static void kStart () {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        DBService dbService = new DBService(sessionFactory);

        searchPageLinks();

        List<String> targetsForStatistics = null; // получить из базы список целей по которым нужна статистика

        dbService.

        Person person = dbService.getPersonByName("Путин");



        List<String> sites = null; // получить из базы список сайтов

        for (String site : sites) {
            for (String target : targetsForStatistics) {
                searchStatistic(target, site);
            }
        }

    }

    private static void searchStatistic(/*каво искать и на каком сайте*/String target, String site) {

        /*ПОИСК ДЛЯ ОДНОГО ЧЕЛОВЕКА*/

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        DBService dbService = new DBService(sessionFactory);

        List<String> pageLinks = null;          /*получить из базы список ссылок на страницы для конкретного сайта*/

        List<String> keyWords = null;           /*запросить из базы список искомых слов*/

        keyWords = dbService.getKeywordByPerson(target);

        int count = 0;                          // количество найденных совпадений


        for (String pageLink : pageLinks) {     // бежим по страницам

            for (String key : keyWords) {       // бежим по искомым словам

                count += PageParser.parsePage(Downloader.download(pageLink),key); // Скачиваем страницу, ищем ключевые слова и записываем количесво найденных совпадений

            }

        }

        /*Записать count в базу*/



        sessionFactory.close();
    }

    private static void searchPageLinks() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        DBService dbService = new DBService(sessionFactory);

        List<Site> sitesList = dbService.getAllSite();       /*получить из базы список ссылок на сайты*/



        for (Site siteLink : sitesList) {                                 // Идём по сайтам

            for (String siteMap : PageParser.searchSiteMap(siteLink.getName())) {     // Ищем сайтМапы и передаём их сразу в цикл для поиска ссылок на страницы

                for (String pageLink : PageParser.parseSiteMap(siteMap)) {    // Ищем ссылки на страницы и записываем их в базу

                    /*Записать ссылку на страницу pageLink в базу*/

                }
            }
        }
        sessionFactory.close();
    }
}
