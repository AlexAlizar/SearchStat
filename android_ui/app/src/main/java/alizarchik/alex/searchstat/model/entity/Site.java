package alizarchik.alex.searchstat.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aoalizarchik.
 */

public class Site {

    private String name;

    public Site(String name) {
        this.name = name;
    }

    public String getSiteName() {
        return name;
    }

}
