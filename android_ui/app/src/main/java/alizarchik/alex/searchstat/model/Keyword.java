package alizarchik.alex.searchstat.model;

/**
 * Created by aoalizarchik.
 */

public class Keyword {

    String name;
    int personId;

    public Keyword(String name, int personId) {
        this.name = name;
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
