package alizarchik.alex.searchstat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GenStatDataItem {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rank")
    @Expose
    private int rank;

    public GenStatDataItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
