package alizarchik.alex.searchstat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button signUpButton;
    private ProgressBar progressBar;
    public static final String TAG = "MyLogs";
    IRestApi restAPI;
    TokenStorage tokenStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        signUpButton.setOnClickListener(view -> startNewActivity(SignUpActivity.class));
        loginButton.setOnClickListener((v) -> onClick());
        tokenStorage = TokenStorage.getInstance();
        if (!tokenStorage.loadToken(this).isEmpty()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initView() {
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progressBar);
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }

    public void onClick() {
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://195.110.59.16:8081/restapi-v3/?")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            Log.d(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        Call<String> call = restAPI.auth(login.getText().toString(), password.getText().toString());
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                authorization(call);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "no retrofit: " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Подключите интернет");
            Toast.makeText(this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void authorization(Call<String> call) throws IOException {
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        String token = response.body();
                        Log.d(TAG, "response.body() token: " + token);
                        tokenStorage.saveToken(token, LoginActivity.this);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d(TAG, "onResponse error: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "You need to register.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
