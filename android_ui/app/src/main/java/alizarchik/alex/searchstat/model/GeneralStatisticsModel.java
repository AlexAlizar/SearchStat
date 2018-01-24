package alizarchik.alex.searchstat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Olesia on 25.12.2017.
 */

public class GeneralStatisticsModel {
    @SerializedName("sites")
    @Expose
    private ArrayList<Site> sites;

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }

}