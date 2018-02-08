package kraulerService.parsingService;

/**
 * Created by User on 21.12.2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by User on 16.12.2017.
 */


public class Downloader {
    final static String DEFAULT_PROTOCOL = "https://";
    final static String ALTERNATIVE_PROTOCOL = "http://";
    final static String DEFAULT_ENCODE = "UTF-8";

    public static String download(String url) {

        String content;

        //url = url.toLowerCase();
        if (hasProtocol(url)) {
            content = getContent(url);
        } else {
            content = getContent(DEFAULT_PROTOCOL + url);
            if (!checkContent(content)) {
                content = getContent(ALTERNATIVE_PROTOCOL + url);
                if (!checkContent(content)) {
                    return "Указан некорректный веб-адрес";
                }
            }
        }
        return content;
    }


    public static String getContent(String url) {

        String page;

        // считываем в стандартной кодировке
        page = loadPage(url, DEFAULT_ENCODE);

        if (!page.equals("Page not found!")) {
            // определяем кодировку скачанной страницы
            String encode = getEncode(page);
            if (!encode.equals("")) {
                if (!encode.equals(DEFAULT_ENCODE)) {
                    page = loadPage(url, encode);
                }
            }
        }
        return page;
    }


    // функция определяет кодировку переданного html-файла
    public static String getEncode(String page) {
        Document doc = null;
        doc = Jsoup.parse(page);
        assert doc != null;
        String charset = "";
        boolean getNext = false;


        List<String> tmpList = doc.select("meta").eachAttr("charset");

        if (tmpList.size() > 0) {
            charset = tmpList.get(0);
        } else {
            tmpList = doc.select("meta").eachAttr("content");
            for (String s : tmpList) {
                if (s.contains("charset")) {
                    for (String a : s.split("[\\s;=]")) {
                        if (getNext) {
                            charset = a;
                            break;
                        }
                        if (a.equals("charset")) getNext = true;
                    }
                }
            }
        }
        return charset;
    }

    // функция получает html-страницу в заданной кодировке
    public static String loadPage(String url, String encode) {

        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            URL site = new URL(url);
            reader = new BufferedReader(new InputStreamReader(site.openStream(), encode));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(" ");
            }

        } catch (java.io.FileNotFoundException e) {
            return "Page not found!";
        } catch (java.io.IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (result.toString().equals(""))
            return "Page not found!";
        else
            return result.toString();

    }





    private static boolean hasProtocol(String url) {

        int index = url.indexOf("://");
        if (index < 0) {
            return false;
        } else {
            return true;
        }
    }


    private static boolean checkContent(String content) {
        if (content.indexOf("<title>301 Moved Permanently</title></head>") > 0) {
            return false;
        } else {
            return true;
        }

    }
}
