package alizarchik.alex.searchstat;


import java.util.List;

import alizarchik.alex.searchstat.Model.GeneralStatisticsModel;
import alizarchik.alex.searchstat.Model.Site;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Olesia on 25.12.2017.
 */

public interface IRestApi {
    TokenStorage tokenStorage = TokenStorage.getInstance();


    @GET("?action=auth")
    Call<String> auth(@Query(value = "login", encoded = true)
                              String login,
                      @Query(value = "password", encoded = true)
                              String password);

    @GET("?action=get-sites")
    Call<List<Site>> getSites(@Query(value = "token")
                               String token);

}
    /*// кодирование используем, чтобы передаваемый URL сайта не конфликтовал с URL API
    @GET("sites")
    Call<List<GeneralStatisticsModel>> loadGeneralStatistics(@Query(value = "urlSite", encoded = true)
                                                                     String urlSite);
    // для теста
    @GET("sites")
    Call<GeneralStatisticsModel> loadSite();

    @GET("sites")
    Call<List<DailyStatisticsModel>> loadDailyStatisticsModel(@Query(value = "urlSite", encoded = true)
                                                                     String urlSite,
                                                              @Query("startDate") int startDate,
                                                              @Query("finalDate") int finalDate);
}*/
