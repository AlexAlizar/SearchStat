package alizarchik.alex.searchstat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aoalizarchik.
 */

public class Site {
    @SerializedName("name")
    @Expose
    private String name;

    public Site(String name) {
        this.name = name;
    }

    public String getSiteName() {
        return name;
    }

    public void setSiteName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                '}';
    }
}
