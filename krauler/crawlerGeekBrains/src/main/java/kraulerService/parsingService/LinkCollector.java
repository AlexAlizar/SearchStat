package kraulerService.parsingService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class LinkCollector {

//    public static void collect(String URL) {
    public static List<String> collect(String URL) {
        List<String> links;
        Document doc = null;

        try {
            doc  = Jsoup.connect(URL).get();
        } catch (IOException e) {
            LogWork.myPrintStackTrace(e);
        }
        assert doc != null;

        links = doc.select("a").eachAttr("href");

/* test * /
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");
        System.out.println("linkHref --- " + linkHref);

        String title = doc.title();
        System.out.println("title --- " + title);
/**/

        System.out.println("links.size before --- " + links.size());

        for (int i = 0; i < links.size(); i++) {
            if (!links.get(i).startsWith(URL)) {
                links.remove(i);
                System.out.println("Remove 1 String -=- " + links.get(i));
            }
        }

        System.out.println("links.size after --- " + links.size());

        return links;
    }
}
