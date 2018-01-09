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
import alizarchik.alex.searchstat.Model.Site;

/**
 * Created by Olesia on 09.01.2018.
 */

public class SitesFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.sites_screen, container, false);

        List<Site> sites = getSites();
        mRecyclerView = view.findViewById(R.id.rvSites);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SitesRecyclerAdapter(sites);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new
                DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.line_divider));
        mRecyclerView.addItemDecoration(divider);

        return view;
    }

    public List<Site> getSites() {
        Site item1 = new Site("Site#1");
        Site item2 = new Site("Site#2");
        Site item3 = new Site("Site#3");
        List<Site> sites = new ArrayList<>();
        sites.add(item1);
        sites.add(item2);
        sites.add(item3);

        return sites;
    }
}
