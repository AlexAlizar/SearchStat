package ru.geekbrains.internship;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionDB {

    private HttpURLConnection dBCon;

    public ConnectionDB(String DBStringURL) throws Exception {
        URL dBUrl = new URL(DBStringURL);
        dBCon = (HttpURLConnection) dBUrl.openConnection();
    }

    public String readDBResult() {
        String out = "";
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(dBCon.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out += inputLine;
            }
            in.close();
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка чтения данных из БД");
            //e.printStackTrace();
        }
        return out;
    }

    public void closeConnectionDB() {
        dBCon.disconnect();
    }

}
