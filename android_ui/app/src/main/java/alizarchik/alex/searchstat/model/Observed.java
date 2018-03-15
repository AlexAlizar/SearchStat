package alizarchik.alex.searchstat.model;

/**
 * Created by Olesia on 13.03.2018.
 */

public interface Observed {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
