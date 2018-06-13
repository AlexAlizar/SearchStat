package alizarchik.alex.searchstat.view;

import java.util.List;

import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;

/**
 * Created by Olesia on 16.05.2018.
 */

public interface IDailyStatView {
    void hideLoadingIndicator();
    void showNoConnectionMessage();
    void showNoConnectionToTheServer();
    void updateSites(List<Site> sites);
    void updatePersons(List<Person> persons);
    void updateStat(List<DailyStatisticsModel> listDS);
    void setStat(List<DailyStatisticsModel> listDS);
}
