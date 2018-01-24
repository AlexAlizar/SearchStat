package ru.geekbrains.internship;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javafx.collections.ObservableList;

abstract class JSONReparsing<T> {

    public void readJSON(String stringURL, ObservableList<T> tList) throws Exception {
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
    }

    protected abstract T readJSONObject(JsonObject jsonObject);
}
