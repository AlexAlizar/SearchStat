package alizarchik.alex.searchstat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Olesia on 25.12.2017.
 */

public class DailyStatisticsModel {
    @SerializedName("person")
    @Expose
    private String person;
    @SerializedName("page")
    @Expose
    private int page;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
