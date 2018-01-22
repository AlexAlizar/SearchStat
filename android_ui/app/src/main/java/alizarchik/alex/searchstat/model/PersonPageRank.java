package alizarchik.alex.searchstat.model;

/**
 * Created by aoalizarchik.
 */

public class PersonPageRank {

    int personId;
    int pageId;
    int rank;

    public PersonPageRank(int personId, int pageId, int rank) {
        this.personId = personId;
        this.pageId = pageId;
        this.rank = rank;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
