package alizarchik.alex.searchstat.presenter;


/**
 * Created by Olesia on 21.04.2018.
 */

public interface IGeneralStatPresenter {
    void loadSites(String token);
    void loadGeneralStat(String token, String site, boolean isUpdate);
}
