package kraulerService.parsingService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class LinkCollector {

//    public static void collect(String URL) {
    public static void collect() {
        String URL = "http://developer.alexanderklimov.ru/android/";
        //
        Document doc = null;

        try {
            doc  = Jsoup.connect(URL).get();
        } catch (IOException e) {
            LogWork.myPrintStackTrace(e);
        }


        Element link = doc.select("a").first();
        List<String> link1 = doc.select("a").eachAttr("href");
        String linkHref = link.attr("href");

        String title = doc.title();

        System.out.println("linkHref --- " + linkHref);
        System.out.println("title --- " + title);

        System.out.println("\n");

        for (String s: link1) {
            if (s.startsWith("http://developer.alexanderklimov.ru"))
            System.out.println(s);
        }








    }

}
