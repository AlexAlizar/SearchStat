package alizarchik.alex.searchstat.model;

import java.util.List;
import alizarchik.alex.searchstat.model.entity.GenStatDataItem;
import alizarchik.alex.searchstat.model.entity.Site;
import rx.Observable;

/**
 * Created by Olesia on 21.04.2018.
 */

public interface IGeneralStatApi {
    Observable<List<Site>> getSites(String token);
    Observable<List<GenStatDataItem>> getGeneralStat(String token, String site);
}
