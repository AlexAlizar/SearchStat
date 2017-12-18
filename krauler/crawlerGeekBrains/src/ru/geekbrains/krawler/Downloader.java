package ru.geekbrains.krawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by User on 16.12.2017.
 */
public class Downloader {

    public static String download(String url) {
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
}
