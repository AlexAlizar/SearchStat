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
import alizarchik.alex.searchstat.model.entity.DailyStatisticsModel;

/**
 * Created by Olesia on 09.01.2018.
 */

public class DSRecyclerAdapter extends RecyclerView.Adapter<DSRecyclerAdapter.ViewHolder> {

    private List<DailyStatisticsModel> dailyStatistics = Collections.emptyList();

    void setDailyStatistics(List<DailyStatisticsModel> dailyStatistics) {
        this.dailyStatistics = dailyStatistics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DSRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_stat_item, parent, false);
        return new DSRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSRecyclerAdapter.ViewHolder holder, int position) {
        DailyStatisticsModel dailyStatisticsModel = dailyStatistics.get(position);
        holder.date.setText(dailyStatisticsModel.getDate().substring(0, 10));
        holder.page.setText(Integer.toString(dailyStatisticsModel.getCountOfPages()));

    }

    @Override
    public int getItemCount() {
        return dailyStatistics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView page;

        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.ds_date_item);
            page = itemView.findViewById(R.id.ds_number_new_pages);
        }
    }
}
