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

import alizarchik.alex.searchstat.Model.GenStatDataItem;

/**
 * Created by Александр on 07.01.2018.
 */

public class GeneralStatFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.general_stat_screen, container, false);

        List<GenStatDataItem> myDataset = getDataSet();
        mRecyclerView = view.findViewById(R.id.rvGenStat);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GSRecyclerAdapter(myDataset);
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
