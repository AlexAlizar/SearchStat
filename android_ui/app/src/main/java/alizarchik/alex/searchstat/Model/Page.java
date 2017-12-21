package alizarchik.alex.searchstat.Model;

import java.util.Date;

/**
 * Created by aoalizarchik.
 */

public class Page {

    String url;
    int siteId;
    Date foundDateTime;
    Date lastScanDate;

    public Page(String url, int siteId, Date foundDateTime, Date lastScanDate) {
        this.url = url;
        this.siteId = siteId;
        this.foundDateTime = foundDateTime;
        this.lastScanDate = lastScanDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
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
