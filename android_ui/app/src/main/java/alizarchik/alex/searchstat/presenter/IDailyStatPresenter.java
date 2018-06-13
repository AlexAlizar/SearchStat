package alizarchik.alex.searchstat.presenter;

/**
 * Created by Olesia on 16.05.2018.
 */

public interface IDailyStatPresenter {
    void loadSites(String token);
    void loadPersons(String token);
    void loadDailyStat(String token, String person, String date1, String date2, String site, boolean isUpdate);
}
