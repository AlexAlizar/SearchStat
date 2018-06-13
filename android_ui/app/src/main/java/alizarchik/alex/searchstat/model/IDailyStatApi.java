package alizarchik.alex.searchstat.model;

import java.util.List;

import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;
import rx.Observable;

/**
 * Created by Olesia on 16.05.2018.
 */

public interface IDailyStatApi {
    Observable<List<Site>> getSites(String token);
    Observable<List<Person>> getPersons(String token);
    Observable<List<DailyStatisticsModel>> getDailyStat(String token, String person, String date1, String date2, String site);
}
