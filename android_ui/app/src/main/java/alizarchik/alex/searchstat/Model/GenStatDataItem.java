package alizarchik.alex.searchstat.Model;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GenStatDataItem {
    private String name;
    private int mentions;

    public GenStatDataItem(String name, int mentions) {
        this.name = name;
        this.mentions = mentions;
    }

    public String getName() {
        return name;
    }

    public int getMentions() {
        return mentions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }
}
