package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 22.12.2017.
 */
@Entity
@Table (name="person_page_rank")
@IdClass(RankKey.class)
public class PersonPageRank implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn (name = "person_id")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn (name = "page_id")
    private Page page;

    @Column(name="rank")
    private int rank;

    public PersonPageRank() {
    }

    public PersonPageRank(Person person, Page page) {
        this.person = person;
        this.page = page;
    }

    public PersonPageRank(Person person, Page page, int rank) {
        this.person = person;
        this.page = page;
        this.rank = rank;
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
