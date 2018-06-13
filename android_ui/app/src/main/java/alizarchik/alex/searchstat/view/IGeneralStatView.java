package alizarchik.alex.searchstat.view;

import java.util.List;

import alizarchik.alex.searchstat.model.entity.GenStatDataItem;
import alizarchik.alex.searchstat.model.entity.Site;

/**
 * Created by Olesia on 21.04.2018.
 */

public interface IGeneralStatView {
    void hideLoadingIndicator();
    void showNoConnectionMessage();
    void showNoConnectionToTheServer();
    void updateSites(List<Site> sites);
    void updateStat(List<GenStatDataItem> listGS);
    void setStat(List<GenStatDataItem> listGS);
}
