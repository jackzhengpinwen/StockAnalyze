package com.zpw.stockanalyze.mvvm.industry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zpw.stockanalyze.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {

    private List<String> tabData;

    private Context mContext;

    public TabAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setTabData(List<String> tabData) {
        this.tabData = tabData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dailyquotes_tab, parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        holder.mTabView.setText(tabData.get(position));
    }

    @Override
    public int getItemCount() {
        return tabData.size() == 0 ? 0 : tabData.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {

        TextView mTabView;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            mTabView = itemView.findViewById(R.id.tabView);
        }
    }
}




