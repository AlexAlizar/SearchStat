package ru.geekbrains.internship;

import com.google.gson.JsonObject;

public class DailyStatisticsJSONReparsing extends JSONReparsing<DailyStatistics> {

    @Override
    public DailyStatistics readJSONObject(JsonObject jsonObject) {
        String stringDate = jsonObject.get("date").getAsString();
        String stringCountOfPages = jsonObject.get("countOfPages").getAsString();
        return new DailyStatistics(stringDate, Integer.parseInt(stringCountOfPages));
    }
}
