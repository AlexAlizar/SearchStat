package alizarchik.alex.searchstat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alizarchik.alex.searchstat.Model.GenStatDataItem;

/**
 * Created by Olesia on 29.12.2017.
 */

public class GSRecyclerAdapter extends RecyclerView.Adapter<GSRecyclerAdapter.ViewHolder> {

    private List<GenStatDataItem> mDataset;

    public GSRecyclerAdapter(List<GenStatDataItem> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_stat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GenStatDataItem genStatDataItem = mDataset.get(position);
        holder.name.setText(genStatDataItem.getName());
        holder.mentions.setText(Integer.toString(genStatDataItem.getMentions()));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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
