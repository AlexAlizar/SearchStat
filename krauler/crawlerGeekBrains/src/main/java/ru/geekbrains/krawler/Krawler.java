package ru.geekbrains.krawler;

import org.hibernate.SessionFactory;

import java.util.List;

public class Krawler {
    public static void kStart () {

        searchPageLinks();

        List<String> targetsForStatistics = null; // получить из базы список целей по которым нужна статистика

        List<String> sites = null; // получить из базы список сайтов

        for (String site : sites) {
            for (String target : targetsForStatistics) {
                searchStatistic(target, site);
            }
        }

    }

    private static void searchStatistic(/*каво искать и на каком сайте*/String target, String site) {

        /*ПОИСК ДЛЯ ОДНОГО ЧЕЛОВЕКА*/

        List<String> pageLinks = null;          /*получить из базы список ссылок на страницы для конкретного сайта*/

        List<String> keyWords = null;           /*запросить из базы список искомых слов*/

        int count = 0;                          // количество найденных совпадений


        for (String pageLink : pageLinks) {     // бежим по страницам

            for (String key : keyWords) {   // бежим по искомым словам

                count += PageParser.parsePage(Downloader.download(pageLink),key);

            }

        }

        /*Записать count в базу*/

    }

    private static void searchPageLinks() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

//        sessionFactory.

        List<String> sitesList = null;       /*получить из базы список ссылок на сайты*/

        for (String siteLink : sitesList) {                                 // Идём по сайтам

            for (String siteMap : PageParser.searchSiteMap(siteLink)) {     // Ищем сайтМапы и передаём их сразу в цикл для поиска ссылок на страницы

                for (String pageLink : PageParser.parseSiteMap(siteMap)) {    // Ищем ссылки на страницы и записываем их в базу

                    /*Записать ссылку на страницу pageLink в базу*/

                }
            }
        }
    }
}
