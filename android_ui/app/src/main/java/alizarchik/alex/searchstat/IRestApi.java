package alizarchik.alex.searchstat;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Olesia on 25.12.2017.
 */

public interface IRestApi {

    @GET("?action=auth")
    Call<String> auth(@Query(value = "login", encoded = true)
                              String login,
                      @Query(value = "password", encoded = true)
                              String password);

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
