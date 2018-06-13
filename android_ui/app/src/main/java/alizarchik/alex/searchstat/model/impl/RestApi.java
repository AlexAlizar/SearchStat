package alizarchik.alex.searchstat.model.impl;

import alizarchik.alex.searchstat.model.IRestService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Olesia on 24.05.2018.
 */

class RestApi {

    private static RestApi instance;

    private RestApi() {
    }

    static RestApi getInstance() {
        if (instance == null) {
            instance = new RestApi();
        }
        return instance;
    }

    IRestService getRestApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://51.15.55.90:8080/restapi-v4/?")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IRestService.class);
    }
}
