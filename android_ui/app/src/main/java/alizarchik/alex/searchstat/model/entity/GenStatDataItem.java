package alizarchik.alex.searchstat.model.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GenStatDataItem implements Serializable{
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

}
