package alizarchik.alex.searchstat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import alizarchik.alex.searchstat.Model.GenStatDataItem;

/**
 * Created by Olesia on 10.01.2018.
 */

public class GeneralStatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView currentDate;

    public static final String TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_stat_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }

    private void init() {
        List<GenStatDataItem> myDataset = getDataSet();
        mRecyclerView = findViewById(R.id.rvGenStat);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GSRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        // рисую линию вокруг элемента списка
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
        currentDate = findViewById(R.id.tvCurrentDate);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E, MMM d, ''yy", Locale.ENGLISH);
        currentDate.setText("Current date: " + formatForDateNow.format(dateNow));
    }

    public List<GenStatDataItem> getDataSet() {
        GenStatDataItem item1 = new GenStatDataItem("Name#1", 231);
        GenStatDataItem item2 = new GenStatDataItem("Name#2", 102);
        GenStatDataItem item3 = new GenStatDataItem("Name#3", 259);
        List<GenStatDataItem> dataSet = new ArrayList<>();
        dataSet.add(item1);
        dataSet.add(item2);
        dataSet.add(item3);

        return dataSet;
    }
}
