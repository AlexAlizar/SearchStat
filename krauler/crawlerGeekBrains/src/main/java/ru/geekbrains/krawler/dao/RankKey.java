package ru.geekbrains.krawler.dao;

import java.io.Serializable;

/**
 * Created by User on 22.12.2017.
 */
public class RankKey implements Serializable {

    private Person person;
    private Page page;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
