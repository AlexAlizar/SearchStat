package alizarchik.alex.searchstat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import alizarchik.alex.searchstat.Model.DailyStatisticsModel;

/**
 * Created by Olesia on 09.01.2018.
 */

public class DailyStatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String TAG = "MyLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_stat_screen, container, false);

        List<DailyStatisticsModel> dailyStatistics = getDailyStatistics();
        mRecyclerView = view.findViewById(R.id.rvDailyStat);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DSRecyclerAdapter(dailyStatistics);
        mRecyclerView.setAdapter(mAdapter);
        // рисую линию вокруг элемента списка
        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);
        return view;
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
