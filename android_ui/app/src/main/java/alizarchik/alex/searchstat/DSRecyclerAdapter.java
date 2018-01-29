package alizarchik.alex.searchstat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import alizarchik.alex.searchstat.model.DailyStatisticsModel;

/**
 * Created by Olesia on 09.01.2018.
 */

public class DSRecyclerAdapter extends RecyclerView.Adapter<DSRecyclerAdapter.ViewHolder> {

    private List<DailyStatisticsModel> dailyStatistics = Collections.emptyList();

    public void setDailyStatistics(List<DailyStatisticsModel> dailyStatistics) {
        this.dailyStatistics = dailyStatistics;
        notifyDataSetChanged();
    }

    @Override
    public DSRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_stat_item, parent, false);
        return new DSRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DSRecyclerAdapter.ViewHolder holder, int position) {
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
