package alizarchik.alex.searchstat.model.impl;

import alizarchik.alex.searchstat.model.ILoginApi;
import retrofit2.Call;

/**
 * Created by Olesia on 11.04.2018.
 */

public class LoginApi implements ILoginApi{

    private RestApi restApi = RestApi.getInstance();

    @Override
    public Call<String> getToken(String login, String password) {

        return restApi.getRestApi().auth(login, password);

    }
}

