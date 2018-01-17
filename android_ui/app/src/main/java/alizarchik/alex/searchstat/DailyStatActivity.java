package alizarchik.alex.searchstat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import alizarchik.alex.searchstat.Model.DailyStatisticsModel;

/**
 * Created by Olesia on 10.01.2018.
 */

public class DailyStatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonSelectPerson;
    private Button buttonSelectSite;
    private Button startDate;
    private Button endDate;

    public static final String TAG = "MyLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stat_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        initSiteSelect();
        initPersonSelect();
    }

    private void initPersonSelect() {
        ArrayList<String> persons = new ArrayList<>();
        persons.add("person#1");
        persons.add("person#2");
        persons.add("person#3");

        final CharSequence[] personsArray = persons.toArray(new String[persons.size()]);

        buttonSelectPerson = findViewById(R.id.button_select_person);
        buttonSelectPerson.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select a person");
            builder.setItems(personsArray, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
                // Do something with the selection
            });
            builder.show();
        });
    }

    private void initSiteSelect() {
        ArrayList<String> sites = new ArrayList<>();
        sites.add("site#1");
        sites.add("site#2");
        sites.add("site#3");

        final CharSequence[] sitesArray = sites.toArray(new String[sites.size()]);

        buttonSelectSite = findViewById(R.id.button_select_site);
        buttonSelectSite.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select a site");
            builder.setItems(sitesArray, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
                // Do something with the selection
            });
            builder.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }

    private void init() {

        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateDialog = new DatePickerStart();
                dateDialog.show(getSupportFragmentManager(), "datePickerStart");
            }
        });
        endDate = findViewById(R.id.end_date);

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateDialog = new DatePickerEnd();
                dateDialog.show(getSupportFragmentManager(), "datePickerEnd");
            }
        });

        List<DailyStatisticsModel> dailyStatistics = getDailyStatistics();
        mRecyclerView = findViewById(R.id.rvDailyStat);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DSRecyclerAdapter(dailyStatistics);
        mRecyclerView.setAdapter(mAdapter);
        // рисую линию вокруг элемента списка
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
    }

    public List<DailyStatisticsModel> getDailyStatistics() {
        DailyStatisticsModel item1 = new DailyStatisticsModel("Name#1", 231);
        DailyStatisticsModel item2 = new DailyStatisticsModel("Name#2", 102);
        DailyStatisticsModel item3 = new DailyStatisticsModel("Name#3", 259);
        List<DailyStatisticsModel> dataSet = new ArrayList<>();
        dataSet.add(item1);
        dataSet.add(item2);
        dataSet.add(item3);

        return dataSet;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.graphics:
                Intent intent = new Intent(this, GraphActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
