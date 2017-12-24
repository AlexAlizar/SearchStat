package ru.geekbrains.krawler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PageParser {
    /**
     * Поиск Sitemap в robots.txt
     *
     * @param robots
     * @return Список ссылок на sitemap'ы
     */
    public static List<String> searchSiteMap(String robots) {

        String keyForSearch = "sitemap:";                   // ключ для поиска ссылки на saitmap
        String robotsLowerCase = robots.toLowerCase();      // приводим строку к нижнему регистру для упрощения поиска
        StringBuilder stringBuilder = new StringBuilder();  // стринг билдер для посимвольного построения найденной ссылки после keyForSearch
        List<String> siteMapXmlLinks = new ArrayList<String>();   // результирующий список для вывода из метода найденных ссылок

        boolean keyFound = false;  // отметка о том что keyForSearch найден или нет
        char separator = ' ';      // разделитель строк в файле который был передан в этот метод в качестве одной строки

        for (int i = 0; i < robotsLowerCase.length(); i++) {         // цикл по символам переданной строки
            for (int j = 0; j < keyForSearch.length(); j++, i++) {   // цикл по символам keyForSearch

                if (!(robotsLowerCase.charAt(i) == keyForSearch.charAt(j))) {  // если символы не равны прерываем цикл по символам keyForSearch
                    keyFound = false;
                    break;
                } else {
                    keyFound = true;
                }
            }

            if (keyFound) { // если keyForSearch найден, берём ссылку и записываем её в список
                for (; ; i++) {
                    if (robots.charAt(i) == separator) continue;

                    System.out.println();
                    for (; robots.charAt(i) != separator; i++) {
                        stringBuilder.append(robots.charAt(i));
                    }
                    siteMapXmlLinks.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                    break;
                }
                keyFound = false;
            }
        }
        return siteMapXmlLinks;
    }

    /**
     * Поиск страниц в Sitemap
     *
     * @param uriSiteMapXml
     * @return Список ссылок из входного sitemap
     */
    public static List<String> parseSiteMap(String uriSiteMapXml) {
        List<String> links = new LinkedList<String>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(uriSiteMapXml);     // Скачиваем страницу
            Node root = document.getDocumentElement();                    // Получаем корневой элемент xml документа
            NodeList nodeList = root.getChildNodes();                     // Получаем список дочерних элиментов
            for (int i = 0; i < nodeList.getLength(); i++) {              //
                Node node = nodeList.item(i);                             //
                NodeList nodeList1 = node.getChildNodes();                //
                for (int j = 0; j < nodeList1.getLength(); j++) {
                    if (nodeList1.item(j).getNodeName().equals("loc")) {  // ищем узлы "loc" и заносим значиения из этих узлов в результирующий список
                        links.addAll(parseUrlSet(nodeList1.item(j).getTextContent()));
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (links.isEmpty()) System.out.println("Ссылок не найдено!");
        return links;
    }

    /**
     * Принемает ссылку на файл и обробатывет его в зависимости от фйормата (xml или нет)
     *
     * @param urlset
     * @return списо ссылок на страницы
     */
    public static List<String> parseUrlSet(String urlset) {
        if (isXml(urlset)) {              // если файл имеет формат xml
            return parseSiteMap(urlset);
        } else {                          // парсим как обычный текст
            return getStringsByUrl(urlset);
        }
    }

    /**
     * Поиск целевых строк в странице и подсчёт их количества
     *
     * @param page
     * @param searchString
     * @return колличество найденных вхождений переданной строки
     */
    public static int parsePage(String page, String searchString) {
        int count = 0;

/**/  // Текущая реализация
        String preparedSearchString = searchString.trim().toLowerCase();
        List<String> words = new ArrayList<String>();

        for (String s : page.split("[\\s,.;:!?« »<=>\"–\\-]")) {
            if (s.trim().toLowerCase().equals(preparedSearchString)) count++;
        }
/**/

/*/ //Предыдущая реализация метода
        //Pattern p = Pattern.compile("\\b" + searchString + "\\b", Pattern.UNICODE_CASE|Pattern.CASE_INSENSITIVE);
        Pattern p = Pattern.compile(searchString, Pattern.UNICODE_CASE|Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(page);
        while(m.find()) count++;
/**/
        return count;
    }


    /**
     * Проверка строки на соответствие формату xml
     *
     * @param urlStr
     * @return true / false
     */
    public static boolean isXml(String urlStr) {

        String adr = urlStr;
        StringBuilder stringBuilder = new StringBuilder();
        URL url = null; //создаем URL
        BufferedReader br;
        String str = null;

        try {
            url = new URL(adr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //открываем соединение
            br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // используем объект класса BufferedReader для работы со строками
            str = br.readLine();

            br.close(); //закрываем поток
            conn.disconnect(); //закрываем соединение
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.contains("<?xml");
    }

    /**
     * Скачивает, по переданной ссылке, построчно файл
     *
     * @param urlStr
     * @return список строк
     */
    public static List<String> getStringsByUrl(String urlStr) {

        String adr = urlStr;
        BufferedReader br;
        String str;
        URL url = null; //создаем URL
        List<String> resultList = new ArrayList<String>();

        try {
            url = new URL(adr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //открываем соединение
            br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // используем объект класса BufferedReader для работы со строками

            while ((str = br.readLine()) != null) { // пока не достигнут конец, считываем страницу построчно
                resultList.add(str);
            }
            br.close(); //закрываем поток
            conn.disconnect(); //закрываем соединение

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

}


