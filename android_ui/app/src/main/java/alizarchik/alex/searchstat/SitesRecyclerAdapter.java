package alizarchik.alex.searchstat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alizarchik.alex.searchstat.Model.GenStatDataItem;
import alizarchik.alex.searchstat.Model.Site;

/**
 * Created by Olesia on 09.01.2018.
 */

public class SitesRecyclerAdapter extends RecyclerView.Adapter<SitesRecyclerAdapter.ViewHolder> {

    private List<Site> sites;

    public SitesRecyclerAdapter(List<Site> sites) {
        this.sites = sites;
    }

    @Override
    public SitesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sites_item, parent, false);
        return new SitesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SitesRecyclerAdapter.ViewHolder holder, int position) {
        Site site = sites.get(position);
        holder.siteName.setText(site.getSiteName());
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView siteName;

        ViewHolder(View itemView) {
            super(itemView);
            siteName = itemView.findViewById(R.id.siteName);
        }
    }
}
