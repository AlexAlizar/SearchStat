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

    public static String download(String url, String Encode) {

        String content;

        url = url.toLowerCase();
        if (hasProtocol(url)) {
            content = getContent(url, Encode);
        } else {
            content = getContent(DEFAULT_PROTOCOL + url, Encode);
            if (!checkContent(content)) {
                content = getContent(ALTERNATIVE_PROTOCOL + url, Encode);
                if (!checkContent(content)) {
                    return "Указан некорректный веб-адрес";
                }
            }
        }
        return content;
    }


    public static String getContent(String url, String Encode) {


        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            URL site = new URL(url);
            if (Encode.equals("UTF-8"))
                reader = new BufferedReader(new InputStreamReader(site.openStream()));
            else reader = new BufferedReader(new InputStreamReader(site.openStream(), "windows-1251"));
            String line;

            while ((line = reader.readLine()) != null) {

                if (Encode.equals("UTF-8"))
                    if (line.contains("windows-1251")) {
                        return "windows-1251";
                    }
                result.append(line);
                result.append(" ");
            }
        } catch (java.io.FileNotFoundException e) {
            return "Page not found!";
        } catch (java.io.IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (reader != null)  reader.close();
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
        if (content.indexOf("<title>301 Moved Permanently</title></head>") > 0) {
            return false;
        } else {
            return true;
        }

    }
}
