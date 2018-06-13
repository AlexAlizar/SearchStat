package alizarchik.alex.searchstat.presenter.impl;

import android.util.Log;
import java.util.List;
import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;
import alizarchik.alex.searchstat.model.impl.DailyStatApi;
import alizarchik.alex.searchstat.presenter.IDailyStatPresenter;
import alizarchik.alex.searchstat.view.IDailyStatView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Olesia on 16.05.2018.
 */

public class DailyStatPresenter implements IDailyStatPresenter {

    private final DailyStatApi model;
    private final IDailyStatView view;
    public static final String TAG = "MyLogs";

    public DailyStatPresenter(IDailyStatView view) {
        this.view = view;
        model = new DailyStatApi();
    }
    @Override
    public void loadSites(String token) {
        Observable<List<Site>> dataObservable = model.getSites(token);
        Log.d(TAG, "dataObservable:" + dataObservable);

        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listSites ->
                {
                    view.hideLoadingIndicator();
                    Log.d(TAG, "listSites:" + listSites);
                    if (listSites.isEmpty()) view.showNoConnectionMessage();
                    else {
                        view.updateSites(listSites);
                    }
                }, error ->{
                    Log.e(TAG, "error:", error);
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                });
    }

    @Override
    public void loadPersons(String token) {
        Observable<List<Person>> dataObservable = model.getPersons(token);
        Log.d(TAG, "dataObservable:" + dataObservable);

        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPersons ->
                {
                    view.hideLoadingIndicator();
                    Log.d(TAG, "listPersons:" + listPersons);
                    if (listPersons.isEmpty()) view.showNoConnectionMessage();
                    else {
                        view.updatePersons(listPersons);
                    }
                }, error ->{
                    Log.e(TAG, "error:", error);
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                });
    }

    @Override
    public void loadDailyStat(String token, String person, String date1, String date2,
                              String site, boolean isUpdate) {
        Observable<List<DailyStatisticsModel>> dataObservable =
                model.getDailyStat(token, person, date1, date2, site);
        Log.d(TAG, "dataObservable DS:" + dataObservable);

        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listDailyStat ->
                {
                    view.hideLoadingIndicator();
                    Log.d(TAG, "listDS:" + listDailyStat);
                    if (listDailyStat.isEmpty()) view.showNoConnectionMessage();
                    else if (isUpdate) {
                        view.updateStat(listDailyStat);
                    } else {
                        view.setStat(listDailyStat);
                    }
                }, error -> {
                    Log.e(TAG, "error:", error);
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                });
    }
}
