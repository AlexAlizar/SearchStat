package ru.geekbrains.krawler;

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

        String content, defaultProtocol, alternativeProtocol;

        url = url.toLowerCase();
        defaultProtocol = checkProtocolURL(url);
        alternativeProtocol = getAlternativeProtocol(defaultProtocol);
        content = getContent(defaultProtocol + url);
        if (!checkContent(content)){
            content = getContent(alternativeProtocol + url);
            if (!checkContent(content)) {
                return "Указан некорректный веб-адрес";
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

    private static String checkProtocolURL(String url) {

        int index = url.indexOf("://");
        if (index < 0) {
            return DEFAULT_PROTOCOL;
        } else {
            return url.substring(0,index+3);
        }
    }

    private static String getAlternativeProtocol(String Protocol) {
        if (Protocol.equals(DEFAULT_PROTOCOL)) {
            return ALTERNATIVE_PROTOCOL;
        } else {
            return DEFAULT_PROTOCOL;
        }
    }

    private static boolean checkContent(String Content) {
        return true;
    }
}
