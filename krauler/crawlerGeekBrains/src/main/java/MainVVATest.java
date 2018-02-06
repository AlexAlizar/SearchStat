import kraulerService.parsingService.PageParser;

import java.util.List;

public class MainVVATest {
    public static void main (String[] args) {
        System.out.println("Start program \n\n");

        PageParser.setLogLevel(2);

        String testURL01 = "https://javatalks.ru";
        String testURL02 = "https://tlgrm.ru";
        String testURL03 = "";
        String testURL04 = "";

        List<String> list = PageParser.collectLinkOnPage(testURL02);

        for (String s: list) {

            System.out.println("list -> " + s);

        }



    }
}
