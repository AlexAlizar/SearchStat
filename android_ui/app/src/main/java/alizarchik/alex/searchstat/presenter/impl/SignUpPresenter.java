package alizarchik.alex.searchstat.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import alizarchik.alex.searchstat.model.impl.SignUpApi;
import alizarchik.alex.searchstat.presenter.ISignUpPresenter;
import alizarchik.alex.searchstat.view.ISignUpView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Olesia on 21.04.2018.
 */

public class SignUpPresenter implements ISignUpPresenter {

    private final SignUpApi model;
    private final ISignUpView view;
    public static final String TAG = "MyLogs";


    public SignUpPresenter(ISignUpView view) {
        this.view = view;
        model = new SignUpApi();
    }

    @Override
    public void registration(String login, String password1, String password2, String email) {
        if (password1.equals(password2)) {
            Call<String> call = model.request(login, password1, email);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        String newUser = response.body();
                        Log.d(TAG, "newUser: " + newUser);
                        view.startLoginActivity();
                    } else {
                        Log.d(TAG, "onResponse error: " + response.code());
                        view.showNoConnectionMessage();
                    }
                    view.hideLoadingIndicator();
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure " + t.getMessage());
                    view.showNoConnectionToTheServer();
                    view.hideLoadingIndicator();
                }
            });
        } else {
            view.showErrorMessage();
            view.hideLoadingIndicator();
        }
    }
}
