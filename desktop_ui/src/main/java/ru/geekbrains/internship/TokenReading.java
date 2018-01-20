package ru.geekbrains.internship;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

class TokenReading {

    public String readToken(String stringURL) {
        String out = "";
        try {
            ConnectionDB connectionDB = new ConnectionDB(stringURL);
            out = connectionDB.readDBResult();
            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка подключения к базе данных");
            //e.printStackTrace();
        } finally {
            return out;
        }
    }

}
