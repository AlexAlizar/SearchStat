package alizarchik.alex.searchstat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olesia on 13.03.2018.
 */

public class DailyStatisticsState implements Observed, Cloneable{
    private String site;
    private String startDate;
    private String endDate;
    private String person;

    List<Observer> observers = new ArrayList<>();

    public void setState(String site, String person, String startDate, String endDate) {
        this.site = site;
        this.person = person;
        this.startDate = startDate;
        this.endDate = endDate;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o :
                observers) {
            o.handleEvent(site, person, startDate, endDate);
        }
    }
}
