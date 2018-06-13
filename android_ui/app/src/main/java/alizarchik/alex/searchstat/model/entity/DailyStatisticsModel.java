package alizarchik.alex.searchstat.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olesia on 25.12.2017.
 */

public class DailyStatisticsModel implements Serializable{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("countOfPages")
    @Expose
    private int countOfPages;

    public DailyStatisticsModel(String date, int countOfPages) {
        this.date = date;
        this.countOfPages = countOfPages;
    }

    public String getDate() {
        return date;
    }

    public int getCountOfPages() {
        return countOfPages;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
