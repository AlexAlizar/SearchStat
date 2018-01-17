import kraulerService.parsingService.LogWork;
import kraulerService.parsingService.RobotsHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class MainTestVVA {

    public static void main(String[] args) {
        LogWork.logWrite("Start program\n");


        RobotsHandler rh = new RobotsHandler(
                new File("/home/vva/testgb/print.txt"), "https://tlgrm.ru"
        );

//        String testLink01 = "https://tlgrm.ru/faq";
//        String testLink01 = "https://tlgrm.ru/";
        String testLink01 = "https://tlgrm.ru/faq/stickers.html";

        System.out.println(testLink01 + "  ->  " + rh.checkLink(testLink01));


        System.out.println("\n");
        // Вывели сайтмапы
        for (String s: rh.getSiteMaps()) System.out.println("SITE_MAP --- " + s);

        // Вывели номер строки где узерагент
//        System.out.println("UserAgentStr --- " + rh.getUserAgentStr());


        // Проверка пттернов регулярных вырожений

//        String test01 = "tlgrm.ru";

//        System.out.println(RobotsHandler.qwe());
//        System.out.println(test01 + " --- " + RobotsHandler.prpareLink(test01));


//        for (String s: rh.getAllowDirective()) System.out.println("+ " + s);
//        for (String s: rh.getDisallowDirective()) System.out.println("- " + s);

/*/
// Тестирую альтернативную библиотеку для парсинга HTML

        String url = "https://stackoverflow.com/questions/2835505";
        File myFile = null;

        Document document = null;
        //document = Jsoup.connect(url).get();
        document = Jsoup.parse("");

        String question = document.select("#question .post-text").text();
        System.out.println("Question: " + question);

        Elements answerers = document.select("#answers .user-details a");
        for (Element answerer : answerers) {
            System.out.println("Answerer: " + answerer.text());
        }
/**/
    }
}
