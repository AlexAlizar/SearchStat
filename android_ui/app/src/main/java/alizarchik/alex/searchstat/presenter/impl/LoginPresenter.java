package alizarchik.alex.searchstat.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import alizarchik.alex.searchstat.model.impl.LoginApi;
import alizarchik.alex.searchstat.presenter.ILoginPresenter;
import alizarchik.alex.searchstat.view.ILoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Olesia on 11.04.2018.
 */

public class LoginPresenter implements ILoginPresenter {
    private final LoginApi model;
    private final ILoginView view;
    public static final String TAG = "MyLogs";

    public LoginPresenter(ILoginView view) {
        this.view = view;
        model = new LoginApi();
    }

    @Override
    public void authorization(String login, String password) {
        Call<String> call = model.getToken(login, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    view.saveToken(token);
                } else {
                    Log.d(TAG, "onResponse error: " + response.code());
                    view.showNoConnectionToTheServer();
                }
                view.hideLoadingIndicator();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                view.showNeedRegisterMessage();
                view.hideLoadingIndicator();
            }
        });
    }
}
