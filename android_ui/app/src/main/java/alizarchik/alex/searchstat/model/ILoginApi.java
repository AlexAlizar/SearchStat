package alizarchik.alex.searchstat.model;

import retrofit2.Call;

/**
 * Created by Olesia on 11.04.2018.
 */

public interface ILoginApi {
    Call<String> getToken(String login, String password);
}
