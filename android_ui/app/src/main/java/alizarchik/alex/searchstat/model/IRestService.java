package alizarchik.alex.searchstat.model;


import java.util.List;
import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.GenStatDataItem;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;
import rx.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Olesia on 25.12.2017.
 */

public interface IRestService {

    @GET("?action=auth")
    Call<String> auth(@Query(value = "login")
                              String login,
                      @Query(value = "password")
                              String password);

    @GET("?action=registration")
    Call<String> createUser(@Query(value = "login")
                                    String login,
                            @Query(value = "password")
                                    String password,
                            @Query(value = "email")
                                    String email);

    @GET("?action=get-sites")
    Observable<List<Site>> getSites(@Query(value = "token")
                                            String token);

    @GET("?action=get-persons")
    Observable<List<Person>> getPersons(@Query(value = "token")
                                                String token);

    // кодирование используем, чтобы передаваемый URL сайта не конфликтовал с URL API
    @GET("?action=general-statistic")
    Observable<List<GenStatDataItem>> getGeneralStatistic(@Query(value = "token")
                                                                  String token,
                                                          @Query(value = "site", encoded = true)
                                                                  String site);

    @GET("?action=daily-statistic")
    Observable<List<DailyStatisticsModel>> getDailyStatistic(@Query(value = "token")
                                                                     String token,
                                                             @Query(value = "person")
                                                                     String person,
                                                             @Query(value = "date1")
                                                                     String date1,
                                                             @Query(value = "date2")
                                                                     String date2,
                                                             @Query(value = "site", encoded = true)
                                                                     String site);

}

