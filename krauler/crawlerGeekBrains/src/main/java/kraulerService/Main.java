package kraulerService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program\n");
//        System.out.println("Проверка связи =)");
        // пример вызова метода download
//        System.out.println(Downloader.download("lenta.kraulerService"));

//        System.out.println("Проверка от Алексей Грунтов =)");

        /*   Тестирую работу с архивами   */

        String fileStorage = "workFileStorage";

        String targetUrl1 = "https://lenta.kraulerService/sitemap.xml.gz";
        String targetUrl2 = "https://lenta.kraulerService";
        String targetUrl3 = "https://yandex.kraulerService";
        String targetUrl4 = "https://yandex.kraulerService/blog/sitemap.xml";
        String targetUrl5 = "https://bfm.kraulerService";

//        File archiveName = getFileByUrl(targetUrl1,fileStorage);

//        System.out.println("archiveName   ->   " + archiveName);

        String robots = Downloader.download(targetUrl5 + "/robots.txt");

        List<String> siteMapList = PageParser.searchSiteMap(robots);



//        List<String> siteMapList = searchSiteMap(getFileByUrl(targetUrl,fileStorage),fileStorage);

        System.out.println("siteMapList SIZE -> " + siteMapList.size());

        for (String s : siteMapList) {
            System.out.println("SITE_MAP   --->   " + s);
        }

        System.out.println("\n   ----------------------   \n");

        for (String s : PageParser.parseUrlSet(targetUrl4)) {

            System.out.println(s);

        }











        /*   ^^^   Тестирую работу с архивами   ^^^   */


        /**
         * чтобы всё работало, надо:
         * 1.1) создать connection в MySQL
         * 1.2) проврить чтобы пароль от созданного connection совпадал с тем под которым вы входите туда
         * 2) создать schema "test"
         * 3) если программа запускается в первый раз, то надо поменять параметр hbm2ddl.auto на create; в последующие запуски надо чтобы этот параметр был равен update
         * 4) ВУАЛЯ - можно использовать методы add/insert Person
         */

//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//
//        DBService dbService = new DBService(sessionFactory);


        // примеры использования методов

        // добавление сайтов

        //  dbService.addSite("lenta.kraulerService");
        //  dbService.addSite("bfm.kraulerService");
        //  dbService.addSite("infox.kraulerService");

        // добавление персон

        //  dbService.addPerson("Путин");
        //  dbService.addPerson("Медведев");
        //  dbService.addPerson("Навальный");

        // добавление страниц
//        Site site = dbService.getSiteByName("lenta.kraulerService");
//        Date date1 = new Date();
//        Date date2 = new Date(date1.getTime() + 86400000); //+1 сутки в миллисекундах
//        dbService.addPage("lenta.kraulerService/testurl.html", site, date1, date2);


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

//        sessionFactory.close();

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
