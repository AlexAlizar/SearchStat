package alizarchik.alex.searchstat.model.impl;

import java.util.List;

import alizarchik.alex.searchstat.model.IDailyStatApi;
import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;
import rx.Observable;

/**
 * Created by Olesia on 16.05.2018.
 */

public class DailyStatApi implements IDailyStatApi {

    private RestApi restApi = RestApi.getInstance();

    @Override
    public Observable<List<Site>> getSites(String token) {

        return restApi.getRestApi().getSites(token);
    }

    @Override
    public Observable<List<Person>> getPersons(String token) {

        return restApi.getRestApi().getPersons(token);
    }

    @Override
    public Observable<List<DailyStatisticsModel>> getDailyStat(String token, String person, String date1, String date2, String site) {

        return restApi.getRestApi().getDailyStatistic(token, person, date1, date2, site);
    }
}
