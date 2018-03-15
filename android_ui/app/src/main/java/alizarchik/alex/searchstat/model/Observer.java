package alizarchik.alex.searchstat.model;

/**
 * Created by Olesia on 13.03.2018.
 */

public interface Observer {
    void handleEvent(String site, String person, String startDate, String endDate);
}
