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

    @Column(name = "SiteID")
    private int siteID;

    @Column (name = "FoundDateTime")
    private Date foundDateTime;

    @Column (name = "LastScanDate")
    private Date lastScanDate;
}
