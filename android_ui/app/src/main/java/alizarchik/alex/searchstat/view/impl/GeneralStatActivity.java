package alizarchik.alex.searchstat.view.impl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import alizarchik.alex.searchstat.R;
import alizarchik.alex.searchstat.model.entity.GenStatDataItem;
import alizarchik.alex.searchstat.model.entity.Site;
import alizarchik.alex.searchstat.presenter.IGeneralStatPresenter;
import alizarchik.alex.searchstat.presenter.impl.GeneralStatPresenter;
import alizarchik.alex.searchstat.view.IGeneralStatView;

/**
 * Created by Olesia on 10.01.2018.
 */

public class GeneralStatActivity extends AppCompatActivity implements IGeneralStatView {

    private IGeneralStatPresenter presenter;
    private GSRecyclerAdapter mAdapter;
    private Button buttonSelectSite;
    private ProgressBar progressBar;
    private List<GenStatDataItem> generalStatList;
    private String site;
    private TokenStorage tokenStorage;

    public static final String TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_stat_screen);
        init();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            if (presenter == null) {
                presenter = new GeneralStatPresenter(this);
            }
        } else {
            showNoConnectionMessage();
        }
        buttonSelectSite.setOnClickListener((v) -> onClickBtnSite());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        generalStatList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar_General_Stat);
        buttonSelectSite = findViewById(R.id.button_select_site_GS);
        RecyclerView mRecyclerView = findViewById(R.id.rvGenStat);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GSRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
        TextView currentDate = findViewById(R.id.tvCurrentDate);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
        currentDate.setText(formatForDateNow.format(dateNow));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.graphics:
                Intent intent = new Intent(this, GraphActivity.class);
                Log.d(TAG, "mListDS для графика: " + generalStatList);
                if (!generalStatList.isEmpty()) {
                    intent.putExtra("activity", "general");
                    intent.putExtra("dataGeneral", new ArrayList<>(generalStatList));
                    startActivity(intent);
                } else {
                    Log.d(TAG, "Выберите сайт");
                    Toast.makeText(GeneralStatActivity.this, R.string.select_a_site, Toast.LENGTH_SHORT).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickBtnSite() {
        tokenStorage = TokenStorage.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        presenter.loadSites(tokenStorage.loadToken(this));
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionMessage() {
        Log.d(TAG, "Подключите интернет");
        Toast.makeText(GeneralStatActivity.this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoConnectionToTheServer() {
        Log.d(TAG, "Hет соединения с сервером");
        Toast.makeText(GeneralStatActivity.this, R.string.no_connection_to_the_server, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSites(List<Site> sites) {
        Log.d(TAG, "mSites " + sites);
        showAlertDialog(sites);
        mAdapter.setDataSet(Collections.emptyList());
    }

    public void showAlertDialog(List<Site> sites) {

        final CharSequence[] sitesArray = new CharSequence[sites.size()];

        for (int i = 0, size = sites.size(); i < size; i++) {
            sitesArray[i] = String.valueOf(sites.get(i).getSiteName());
        }
        Log.d(TAG, "sitesArray " + Arrays.toString(sitesArray));

        AlertDialog.Builder builder = new AlertDialog.Builder(GeneralStatActivity.this);
        builder.setTitle(R.string.select_a_site);
        builder.setItems(sitesArray, (dialogInterface, i) -> {
            buttonSelectSite.setText(sitesArray[i]);
            site = sitesArray[i].toString();
            showGeneralStat(site);
        });
        builder.show();
    }

    public void showGeneralStat(String site) {
        if (tokenStorage != null) {
            Log.d(TAG, "tokenStorage " + tokenStorage);
            presenter.loadGeneralStat(tokenStorage.loadToken(this), site, false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setStat(List<GenStatDataItem> listGeneralStat) {
        generalStatList = listGeneralStat;
        Log.d(TAG, "setStat " + generalStatList);
        mAdapter.setDataSet(generalStatList);
    }

    @Override
    public void updateStat(List<GenStatDataItem> listGeneralStat) {
        generalStatList = listGeneralStat;
        Log.d(TAG, "updateStat " + generalStatList);
        mAdapter.notifyDataSetChanged();
    }
}