package alizarchik.alex.searchstat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

    public static final String TAG = "MyLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stat_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics, menu);
        return true;
    }

    private void init() {
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
}
