package ru.geekbrains.krawler.dao;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by User on 22.12.2017.
 */
@Entity
@Table(name="pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="url", length = 2048)
    private String url;

    @ManyToOne
    @JoinColumn(name="SiteID")
    private Site site;

    @Column (name = "FoundDateTime")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date foundDateTime;

    @Column (name = "LastScanDate")
    @Temporal(value=TemporalType.DATE)
    private Date lastScanDate;

    public Page() {
    }

    public Page(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Date getFoundDateTime() {
        return foundDateTime;
    }

    public void setFoundDateTime(Date foundDateTime) {
        this.foundDateTime = foundDateTime;
    }

    public Date getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Date lastScanDate) {
        this.lastScanDate = lastScanDate;
    }
}
