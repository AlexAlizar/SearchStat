package alizarchik.alex.searchstat.view.impl;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import alizarchik.alex.searchstat.R;
import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.entity.Person;
import alizarchik.alex.searchstat.model.entity.Site;
import alizarchik.alex.searchstat.presenter.IDailyStatPresenter;
import alizarchik.alex.searchstat.presenter.impl.DailyStatPresenter;
import alizarchik.alex.searchstat.view.IDailyStatView;

/**
 * Created by Olesia on 10.01.2018.
 */

public class DailyStatActivity extends AppCompatActivity implements IDailyStatView {

    private IDailyStatPresenter presenter;
    private DSRecyclerAdapter adapter;
    private Button buttonSelectSite;
    private Button buttonSelectPerson;
    private TextView startDate;
    private TextView endDate;
    private ProgressBar progressBar;
    private List<Site> sites;
    private List<Person> persons;
    private String site;
    private String person;
    private String date1;
    private String date2;
    private List<DailyStatisticsModel> dailyStatisticList;
    private TokenStorage tokenStorage;

    public static final String TAG = "MyLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stat_screen);
        init();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            if (presenter == null) {
                presenter = new DailyStatPresenter(this);
            }
        } else {
            showNoConnectionMessage();
        }
        buttonSelectSite.setOnClickListener((v) -> onClickBtnSite());
        buttonSelectPerson.setOnClickListener((v)-> onClickBtnPerson());
        startDate.setOnClickListener(view -> {
            DialogFragment dateDialog = new DatePickerStartFragment();
            dateDialog.show(getSupportFragmentManager(), "datePickerStart");
        });
        endDate.setOnClickListener(view -> {
            DialogFragment dateDialog = new DatePickerEndFragment();
            dateDialog.show(getSupportFragmentManager(), "datePickerEnd");
        });
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tokenStorage = TokenStorage.getInstance();
        buttonSelectSite = findViewById(R.id.button_select_site_DS);
        buttonSelectPerson = findViewById(R.id.button_select_person);
        dailyStatisticList = new ArrayList<>();
        sites = new ArrayList<>();
        persons = new ArrayList<>();
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        RecyclerView mRecyclerView = findViewById(R.id.rvDailyStat);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new DSRecyclerAdapter();
        mRecyclerView.setAdapter(adapter);
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
        progressBar = findViewById(R.id.progressBar_Daily_Stat);
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
                Log.d(TAG, "dailyStatisticList для графика: " + dailyStatisticList);
                if (!dailyStatisticList.isEmpty()) {
                    intent.putExtra("activity", "daily");
                    intent.putExtra("dataDaily", new ArrayList<>(dailyStatisticList));
                    startActivity(intent);
                }else {
                    Log.d(TAG, "Выберите сайт и личность");
                    Toast.makeText(DailyStatActivity.this, R.string.select_site_and_person, Toast.LENGTH_SHORT).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onClickBtnSite() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.loadSites(tokenStorage.loadToken(this));
    }

    private void onClickBtnPerson() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.loadPersons(tokenStorage.loadToken(this));
    }

    public void showDailyStat(String person, String date1, String date2, String site) {
        if (tokenStorage != null) {
            Log.d(TAG, "tokenStorage " + tokenStorage);
            presenter.loadDailyStat(tokenStorage.loadToken(this), person, date1, date2, site, false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateSites(List<Site> sites) {
        this.sites = sites;
        Log.d(TAG, "sites " + this.sites);
        showAlertDialogSites(this.sites);
        adapter.setDailyStatistics(Collections.emptyList());
    }

    @Override
    public void updatePersons(List<Person> persons) {
        this.persons = persons;
        Log.d(TAG, "personList " + this.persons);
        showAlertDialogPersons(this.persons);
        adapter.setDailyStatistics(Collections.emptyList());
    }

    @Override
    public void updateStat(List<DailyStatisticsModel> listDailyStat) {
        dailyStatisticList = listDailyStat;
        Log.d(TAG, "updateStat " + dailyStatisticList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setStat(List<DailyStatisticsModel> listDailyStat) {
        dailyStatisticList = listDailyStat;
        Log.d(TAG, "setStat " + dailyStatisticList);
        adapter.setDailyStatistics(dailyStatisticList);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionMessage() {
        Log.d(TAG, "Подключите интернет");
        Toast.makeText(DailyStatActivity.this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoConnectionToTheServer() {
        Log.d(TAG, "Hет соединения с сервером");
        Toast.makeText(DailyStatActivity.this, R.string.no_connection_to_the_server, Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialogSites(List<Site> sites) {

        final CharSequence[] sitesArray = new CharSequence[sites.size()];

        for (int i = 0, size = sites.size(); i < size; i++) {
            sitesArray[i] = String.valueOf(sites.get(i).getSiteName());
        }
        Log.d(TAG, "sitesArray " + Arrays.toString(sitesArray));

        AlertDialog.Builder builder = new AlertDialog.Builder(DailyStatActivity.this);
        builder.setTitle(R.string.select_a_site);
        builder.setItems(sitesArray, (dialogInterface, i) -> {
            buttonSelectSite.setText(sitesArray[i]);
            site = sitesArray[i].toString();
            if (person != null){
                showDailyStat(person, date1, date2, site);
            }
        });
        builder.show();
    }

    public void showAlertDialogPersons(List<Person> persons) {

        final CharSequence[] personsArray = new CharSequence[persons.size()];

        for (int i = 0, size = persons.size(); i < size; i++) {
            personsArray[i] = String.valueOf(persons.get(i).getName());
        }
        Log.d(TAG, "personsArray " + Arrays.toString(personsArray));

        AlertDialog.Builder builder = new AlertDialog.Builder(DailyStatActivity.this);
        builder.setTitle(R.string.select_a_person);
        builder.setItems(personsArray, (dialogInterface, i) -> {
            buttonSelectPerson.setText(personsArray[i]);
            person = personsArray[i].toString();
            showDailyStat(person, date1, date2, site);
        });
        builder.show();
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
}
