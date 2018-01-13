package kraulerService.parsingService;

import java.io.*;
import java.util.List;

public class RobotsHandler {

    File robots;

    public RobotsHandler(File robots) {
        this.robots = robots;
    }

    public List<String> getSitemap () {

        String keyString = "sitemap";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(robots));
            String tmpLine;
            for (;(tmpLine = bufferedReader.readLine()) != null;) {



                // debug //
//                System.out.println(" --- " + tmpLine);
/**/
                if (tmpLine.toLowerCase().trim().startsWith(keyString)) {
                    System.out.println("Нашёл сайтмап --- " + tmpLine);
                }
/**/
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogWork.myPrintStackTrace(e);
        } catch (IOException e) {
            e.printStackTrace();
            LogWork.myPrintStackTrace(e);
        }

        return null;
    }

    /**
     * User-agent: SearchStat # Наш юзер агент
     * User-agent: * # Любой юзер агент, если не указан явно
     * Disallow: / # Запрещены все страницы
     *
     *
     * Пример (старт) ----------------------------------
     User-Agent: *
     Allow: /
     Allow: /sources
     Allow: /web
     Allow: /apps
     Allow: /faq
     Allow: /press
     Allow: /blog
     Allow: /privacy
     Allow: /techfaq
     Allow: /stickers
     Allow: /sitemap.xml
     Disallow: /about
     Disallow: /language
     Disallow: /manifest.json
     Disallow: /changelog
     Host: https://tlgrm.ru
     Sitemap: https://tlgrm.ru/sitemap.xml
     * Пример (конец) ----------------------------------
     *
     *
     */

    /**
     * Метод получает на фход файл и находит в нём ЧТО?
     * @param robots
     */
    public static void qwe(File robots) {





        //
    }

}
