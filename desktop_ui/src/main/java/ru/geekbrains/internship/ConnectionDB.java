package ru.geekbrains.internship;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionDB implements ConnectionDBConst {

    private HttpURLConnection dBCon;

    public ConnectionDB() throws Exception {
        URL dBUrl = new URL(DBSTRINGURL);
        dBCon = (HttpURLConnection) dBUrl.openConnection();
    }

    public String readDBResult() throws Exception {
        InputStream is = dBCon.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String out = "";

        while(br.ready()) {
            out += br.readLine();
        }
        br.close();
        is.close();
        return out;
    }

    public void closeConnectionDB() {
        dBCon.disconnect();
    }

}
