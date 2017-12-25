package ru.geekbrains.internship;

import com.google.gson.JsonObject;

public interface JSONReparsing {

    public Statistics readJSONObject(JsonObject jsonObject);
}
