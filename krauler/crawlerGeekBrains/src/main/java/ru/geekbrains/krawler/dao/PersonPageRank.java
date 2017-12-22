package ru.geekbrains.krawler.dao;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 22.12.2017.
 */
@Entity
@Table (name="personPageRank")
@IdClass(RankKey.class)
public class PersonPageRank implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn (name = "personID")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn (name = "pageID")
    private Page page;

    @Column(name="rank")
    private int rank;

    public PersonPageRank() {
    }

    public PersonPageRank(Person person, Page page) {
        this.person = person;
        this.page = page;
    }

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
