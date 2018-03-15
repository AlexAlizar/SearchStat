package alizarchik.alex.searchstat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import alizarchik.alex.searchstat.model.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.DailyStatisticsState;
import alizarchik.alex.searchstat.model.GenStatDataItem;
import alizarchik.alex.searchstat.model.Observed;
import alizarchik.alex.searchstat.model.Observer;
import alizarchik.alex.searchstat.model.Person;
import alizarchik.alex.searchstat.model.Site;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Olesia on 10.01.2018.
 */

public class DailyStatActivity extends AppCompatActivity implements Observer {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private DSRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonSelectSite;
    private Button buttonSelectPerson;
    private TextView startDate;
    private TextView endDate;
    private ProgressBar progressBar;
    private ArrayList<String> sites;
    private ArrayList<String> persons;
    private String site;
    private String person;
    private String date1;
    private String date2;
    private DailyStatisticsState state;
    private ArrayList<DailyStatisticsModel> listDS;

    IRestApi restAPI;

    public static final String TAG = "MyLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stat_screen);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        buttonSelectSite = findViewById(R.id.button_select_site_DS);
        buttonSelectPerson = findViewById(R.id.button_select_person);
        buttonSelectSite.setOnClickListener((v) -> onClickBtnSite());
        buttonSelectPerson.setOnClickListener((v) -> onClickBtnPerson());
        //buttonShowStatistic.setOnClickListener((v)-> onClickShowDailyStat(site, person, date1, date2));

    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.graphics:
                Intent intent = new Intent(this, GraphActivity.class);

                intent.putExtra("activity", "daily");
                intent.putExtra("dataDaily", listDS);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        listDS = new ArrayList<>();
        sites = new ArrayList<>();
        persons = new ArrayList<>();
        state = new DailyStatisticsState();
        state.addObserver(DailyStatActivity.this);
        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(view -> {
            DialogFragment dateDialog = new DatePickerStart();
            dateDialog.show(getSupportFragmentManager(), "datePickerStart");
            state.setState(site, person, date1, date2);
        });
        endDate = findViewById(R.id.end_date);

        endDate.setOnClickListener(view -> {
            DialogFragment dateDialog = new DatePickerEnd();
            dateDialog.show(getSupportFragmentManager(), "datePickerEnd");
            state.setState(site, person, date1, date2);
        });

        mRecyclerView = findViewById(R.id.rvDailyStat);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DSRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        // рисую линию вокруг элемента списка
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
        progressBar = findViewById(R.id.progressBar_DS);
    }

    public void onClickBtnSite() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://51.15.55.90:8080/restapi-v4/?")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            Log.d(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        TokenStorage tokenStorage = TokenStorage.getInstance();
        Log.d(TAG, "token from storage: " + tokenStorage.loadToken(this));
        Call<List<Site>> call = restAPI.getSites(tokenStorage.loadToken(this));
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                loadSites(call);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "no retrofit: " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Подключите интернет");
            Toast.makeText(this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSites(Call<List<Site>> call) throws IOException {
        call.enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
                if (response.isSuccessful()) {
                    if (response != null && sites.isEmpty()) {
                        Site site;
                        for (int i = 0; i < response.body().size(); i++) {
                            site = response.body().get(i);
                            //Log.d(TAG, "response.body() sites:" + site.getSiteName());
                            sites.add(site.getSiteName());
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse error: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
                final CharSequence[] sitesArray = sites.toArray(new String[sites.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(DailyStatActivity.this);
                builder.setTitle(R.string.select_a_site);
                builder.setItems(sitesArray, (dialogInterface, i) -> {
                    buttonSelectSite.setText(sitesArray[i]);
                    site = sitesArray[i].toString();
                    state.setState(site, person, date1, date2);
                });
                builder.show();
            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void onClickBtnPerson() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://51.15.55.90:8080/restapi-v4/?")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            Log.d(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        TokenStorage tokenStorage = TokenStorage.getInstance();
        Log.d(TAG, "token from storage: " + tokenStorage.loadToken(this));
        Call<List<Person>> call = restAPI.getPersons(tokenStorage.loadToken(this));
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                loadPersons(call);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "no retrofit: " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Подключите интернет");
            Toast.makeText(this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPersons(Call<List<Person>> call) throws IOException {
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.isSuccessful()) {
                    if (response != null && persons.isEmpty()) {
                        Person person;
                        for (int i = 0; i < response.body().size(); i++) {
                            person = response.body().get(i);
                            //Log.d(TAG, "response.body() persons:" + person.getName());
                            persons.add(person.getName());
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse error: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
                final CharSequence[] sitesArray = persons.toArray(new String[persons.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(DailyStatActivity.this);
                builder.setTitle(R.string.select_a_person);
                builder.setItems(sitesArray, (dialogInterface, i) -> {
                    buttonSelectPerson.setText(sitesArray[i]);
                    person = sitesArray[i].toString();
                    state.setState(site, person, date1, date2);

                });
                builder.show();
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void onClickShowDailyStat(String site, String person, String date1, String date2) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://51.15.55.90:8080/restapi-v4/?")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            Log.d(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        TokenStorage tokenStorage = TokenStorage.getInstance();
        Log.d(TAG, "token from storage: " + tokenStorage.loadToken(this));
        Log.d(TAG, "site: " + site);
        Log.d(TAG, "person: " + person);
        Log.d(TAG, "date1: " + date1);
        Log.d(TAG, "date2: " + date2);

        Call<List<DailyStatisticsModel>> call = restAPI.getDailyStatistic(tokenStorage.loadToken(this), person, date1, date2, site);
        Log.d(TAG, "call: " + call.toString());
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                loadDailyStatistic(call);
                listDS.clear();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "no retrofit: " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Подключите интернет");
            Toast.makeText(this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDailyStatistic(Call<List<DailyStatisticsModel>> call) throws IOException {
        call.enqueue(new Callback<List<DailyStatisticsModel>>() {
            @Override
            public void onResponse(Call<List<DailyStatisticsModel>> call, Response<List<DailyStatisticsModel>> response) {
                if (response.isSuccessful()) {
                    if (response != null && listDS.isEmpty()) {
                        DailyStatisticsModel dailyStatisticsModel;
                        for (int i = 0; i < response.body().size(); i++) {
                            dailyStatisticsModel = response.body().get(i);
                            //Log.d(TAG, "response.body() DS date:" + dailyStatisticsModel.getDate());
                            //Log.d(TAG, "response.body() DS page:" + dailyStatisticsModel.getCountOfPages());
                            listDS.add(dailyStatisticsModel);
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse error: " + response.code());
                }
                mAdapter.setDailyStatistics(listDS);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DailyStatisticsModel>> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void handleEvent(String site, String person, String startDate, String endDate) {
        onClickShowDailyStat(site, person, startDate, endDate);
    }
}
