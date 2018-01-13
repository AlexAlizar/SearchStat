package kraulerService.parsingService;

import java.io.File;

public class TmpVVA {

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
    public static void robotsHandler(File robots) {





        //
    }

}
