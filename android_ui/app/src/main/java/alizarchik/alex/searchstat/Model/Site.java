package alizarchik.alex.searchstat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aoalizarchik.
 */

public class Site {
    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("persons")
    @Expose
    private ArrayList<Person> persons;

    public Site(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteName='" + siteName + '\'' +
                '}';
    }
}
