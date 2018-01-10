package alizarchik.alex.searchstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by Александр on 07.01.2018.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonGS;
    private Button buttonDS;
    private Button buttonPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonGS = findViewById(R.id.button_GS);
        buttonDS = findViewById(R.id.button_DS);
        buttonPS = findViewById(R.id.button_PS);
        buttonGS.setOnClickListener(view -> startNewActivity(GeneralStatActivity.class));
        buttonDS.setOnClickListener(view -> startNewActivity(DailyStatActivity.class));
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }
}

/*public void onClick() {
        mInfoTextView.setText("");
        // для логирования ретрофита
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://195.110.59.16:8081/rest-api-servlet/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            mInfoTextView.setText("no retrofit: " + io.getMessage());
            Log.e(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        Call<GeneralStatisticsModel> call = restAPI.loadSite();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                downloadOneUrl(call);
            } catch (IOException e) {
                e.printStackTrace();
                mInfoTextView.setText(e.getMessage());
                Log.e(TAG, e.getMessage());
            }
        } else {
            Toast.makeText(MainActivity.this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadOneUrl(Call<GeneralStatisticsModel> call) throws IOException {
        call.enqueue(new Callback<GeneralStatisticsModel>() {
            @Override
            public void onResponse(Call<GeneralStatisticsModel> call, Response<GeneralStatisticsModel> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        GeneralStatisticsModel curGeneralModel = null;
                        curGeneralModel = response.body();
                        mInfoTextView.append("\nSites = " + curGeneralModel.getSites() +
                                "\n-----------------");
                    }
                } else {
                    Log.e(TAG, "onResponse error: " + response.code());
                    mInfoTextView.setText("onResponse error: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GeneralStatisticsModel> call, Throwable t) {
                Log.e(TAG, "onFailure ", t);
                mInfoTextView.setText("onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }*/


