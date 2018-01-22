package kraulerService.parsingService;

/**
 * Created by User on 21.12.2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by User on 16.12.2017.
 */


public class Downloader {
    final static String DEFAULT_PROTOCOL = "https://";
    final static String ALTERNATIVE_PROTOCOL = "http://";

    public static String download(String url) {

        String content;

        url = url.toLowerCase();
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

        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            URL site = new URL(url);
            reader = new BufferedReader(new InputStreamReader(site.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(" ");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        if (content.indexOf("<title>301 Moved Permanently</title></head>")>0) {
            return false;
        } else {
            return true;
        }

    }
}
