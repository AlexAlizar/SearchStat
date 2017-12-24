package dbService.dataSets;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by User on 22.12.2017.
 */
@Entity
@Table(name="pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Page(String url, Site site, Date foundDateTime, Date lastScanDate) {
        this.url = url;
        this.site = site;
        this.foundDateTime = foundDateTime;
        this.lastScanDate = lastScanDate;
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

    @Override
    public String toString() {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        return "Page {" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", site='" + site.getName() + '\'' +
                ", FoundDateTime='" + sdfDateTime.format(foundDateTime) + '\'' +
                ", LastScanDate='" + sdfDate.format(lastScanDate) + '\'' +
                "}";
    }
}
