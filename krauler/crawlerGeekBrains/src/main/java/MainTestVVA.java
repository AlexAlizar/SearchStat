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


        RobotsHandler rh = new RobotsHandler(new File("/home/vva/testgb/print.txt"));

        rh.getSitemap();











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
