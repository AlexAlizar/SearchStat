package ru.geekbrains.internship;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

abstract public class JSONReparsing<T> {

    public void readJSON(String stringURL, ObservableList<T> tList) {
        try {
            ConnectionDB connectionDB = new ConnectionDB(stringURL);
            String out = connectionDB.readDBResult();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(out);
            for (int j = 0; j < jsonArray.size(); j++) {
                if (jsonArray.get(j) instanceof JsonPrimitive) {
                    tList.add((T)jsonArray.get(j).getAsString());
                } else {
                    JsonObject personJSON = (JsonObject) jsonArray.get(j);
                    tList.add(readJSONObject(personJSON));
                }
            }
            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка подключения к базе данных");
            //e.printStackTrace();
        }
    }

    abstract public T readJSONObject(JsonObject jsonObject);
}
