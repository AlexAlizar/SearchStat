package alizarchik.alex.searchstat.presenter.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import alizarchik.alex.searchstat.model.entity.GenStatDataItem;
import alizarchik.alex.searchstat.model.entity.Site;
import alizarchik.alex.searchstat.model.impl.GeneralStatApi;
import alizarchik.alex.searchstat.presenter.IGeneralStatPresenter;
import alizarchik.alex.searchstat.view.IGeneralStatView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Olesia on 21.04.2018.
 */

public class GeneralStatPresenter implements IGeneralStatPresenter {

    private final GeneralStatApi model;
    private final IGeneralStatView view;
    public static final String TAG = "MyLogs";

    public GeneralStatPresenter(IGeneralStatView view) {
        this.view = view;
        model = new GeneralStatApi();
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
                }, error -> {
                    Log.e(TAG, "error:", error);
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                });
    }

    @Override
    public void loadGeneralStat(String token, String site, boolean isUpdate) {
        Observable<List<GenStatDataItem>> dataObservable = model.getGeneralStat(token, site);
        Log.d(TAG, "dataObservable:" + dataObservable);

        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGeneralStat ->
                {
                    Collections.sort(listGeneralStat, new RankComparator());
                    view.hideLoadingIndicator();
                    Log.d(TAG, "listGS:" + listGeneralStat);
                    if (listGeneralStat.isEmpty()) view.showNoConnectionMessage();
                    else if (isUpdate) {
                        view.updateStat(listGeneralStat);
                    } else {
                        view.setStat(listGeneralStat);
                    }
                }, error -> {
                    Log.e(TAG, "error:", error);
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                });

    }
}
