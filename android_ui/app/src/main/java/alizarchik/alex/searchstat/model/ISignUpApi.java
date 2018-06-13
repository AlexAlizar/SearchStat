package alizarchik.alex.searchstat.model;

import retrofit2.Call;

/**
 * Created by Olesia on 21.04.2018.
 */

public interface ISignUpApi {
    Call<String> request(String login, String password, String email);
}
