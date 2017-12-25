package ru.geekbrains.internship;

import com.google.gson.JsonObject;

public class TotalStatisticsJSONReparsing implements JSONReparsing {

    @Override
    public Statistics readJSONObject(JsonObject jsonObject) {
        String stringPersonID = jsonObject.get("PersonID").getAsString();
        String stringPersonName = jsonObject.get("PersonName").getAsString();
        String stringPersonRank = jsonObject.get("PersonRank").getAsString();
/*
        System.out.println("\t"+"PersonID = "+ stringPersonID + " PersonName = " + stringPersonName +
                " PersonRank = " + stringPersonRank);
*/
        return new TotalStatistics(stringPersonName, Integer.parseInt(stringPersonRank));
    }
}
