package ru.geekbrains.internship;

import com.google.gson.JsonObject;

public class StringJSONReparsing extends JSONReparsing<String> {

    @Override
    public String readJSONObject(JsonObject jsonObject) {
        return jsonObject.getAsString();
    }
}
