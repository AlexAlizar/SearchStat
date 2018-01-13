package dbService.dataSets;

import javax.persistence.*;

@Entity
@Table(name="sites")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", length = 256)
    private String name;

    private Site() {
    }

    public Site(String name) {
        this.name = name;
    }

    public Site(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site {" +
                "id=" + id +
                ", name='" + name +'\'' +
                "}";
    }
}
