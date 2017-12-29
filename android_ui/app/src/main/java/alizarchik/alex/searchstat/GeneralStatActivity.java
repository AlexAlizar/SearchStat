package alizarchik.alex.searchstat;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alizarchik.alex.searchstat.Model.GenStatDataItem;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GeneralStatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_stat_screen);

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
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(),
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
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
