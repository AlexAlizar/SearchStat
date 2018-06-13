package alizarchik.alex.searchstat.model.impl;

import alizarchik.alex.searchstat.model.ISignUpApi;
import retrofit2.Call;

/**
 * Created by Olesia on 21.04.2018.
 */

public class SignUpApi implements ISignUpApi {

    private RestApi restApi = RestApi.getInstance();

    @Override
    public Call<String> request(String login, String password, String email) {

        return restApi.getRestApi().createUser(login, password, email);
    }
}
