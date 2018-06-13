package alizarchik.alex.searchstat.view.impl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import alizarchik.alex.searchstat.R;
import alizarchik.alex.searchstat.model.entity.GenStatDataItem;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GSRecyclerAdapter extends RecyclerView.Adapter<GSRecyclerAdapter.ViewHolder> {

    private List<GenStatDataItem> dataSet = Collections.emptyList();

    void setDataSet(List<GenStatDataItem> listGenStat) {
        this.dataSet = listGenStat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_stat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenStatDataItem genStatDataItem = dataSet.get(position);
        holder.name.setText(genStatDataItem.getName());
        holder.mentions.setText(Integer.toString(genStatDataItem.getRank()));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView mentions;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.gs_name_item);
            mentions = itemView.findViewById(R.id.gs_mentions_item);
        }
    }
}
