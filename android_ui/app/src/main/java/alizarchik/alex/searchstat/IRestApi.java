package alizarchik.alex.searchstat;


import java.util.List;

import alizarchik.alex.searchstat.Model.GenStatDataItem;
import alizarchik.alex.searchstat.Model.GeneralStatisticsModel;
import alizarchik.alex.searchstat.Model.Site;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Olesia on 25.12.2017.
 */

public interface IRestApi {

    @GET("?action=auth")
    Call<String> auth(@Query(value = "login")
                              String login,
                      @Query(value = "password")
                              String password);

    @GET("?action=get-sites")
    Call<List<Site>> getSites(@Query(value = "token")
                                      String token);

    // кодирование используем, чтобы передаваемый URL сайта не конфликтовал с URL API
    @GET("?action=general-statistic")
    Call<List<GenStatDataItem>> getGeneralStatistic(@Query(value = "token")
                                                 String token,
                                                    @Query(value = "site", encoded = true)
                                                 String site);

}

