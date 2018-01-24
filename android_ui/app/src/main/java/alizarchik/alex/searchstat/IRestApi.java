package alizarchik.alex.searchstat;


import java.util.List;

import alizarchik.alex.searchstat.model.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.GenStatDataItem;
import alizarchik.alex.searchstat.model.Person;
import alizarchik.alex.searchstat.model.Site;
import alizarchik.alex.searchstat.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("?action=get-persons")
    Call<List<Person>> getPersons(@Query(value = "token")
                                          String token);

    // кодирование используем, чтобы передаваемый URL сайта не конфликтовал с URL API
    @GET("?action=general-statistic")
    Call<List<GenStatDataItem>> getGeneralStatistic(@Query(value = "token")
                                                            String token,
                                                    @Query(value = "site", encoded = true)
                                                            String site);

    @GET("?action=daily-statistic")
    Call<List<DailyStatisticsModel>> getDailyStatistic(@Query(value = "token")
                                                               String token,
                                                       @Query(value = "person")
                                                               String person,
                                                       @Query(value = "date1")
                                                               String date1,
                                                       @Query(value = "date2")
                                                               String date2,
                                                       @Query(value = "site", encoded = true)
                                                               String site);

    @POST("?action=add-user")
    Call<User> createUser(@Body User user);
}

