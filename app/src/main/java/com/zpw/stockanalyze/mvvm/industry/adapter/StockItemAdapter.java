package com.zpw.stockanalyze.mvvm.industry.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zpw.stockanalyze.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockItemAdapter extends RecyclerView.Adapter<StockItemAdapter.ItemViewHolder> {

    private List<String> detailBeans;

    private Activity activity;

    public StockItemAdapter(Activity activity) {
        this.activity = activity;
    }


    public void setDetailBeans(List<String> detailBeans) {
        this.detailBeans = detailBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_dailyquotes_content_detail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(position == 0 || position == 1) {
            holder.mTabView.setText(detailBeans.get(position)  + "%");
            float value = Float.valueOf(detailBeans.get(position));
            if(value < 0) {
                holder.mTabView.setTextColor(activity.getResources().getColor(R.color.greenColor));// 绿色
            } else if (value > 0){
                holder.mTabView.setTextColor(activity.getResources().getColor(R.color.redColor));// 红色
            } else {
                holder.mTabView.setTextColor(activity.getResources().getColor(R.color.tabTextColor));// 灰色
            }
        } else {
            holder.mTabView.setText(detailBeans.get(position));
            holder.mTabView.setTextColor(activity.getResources().getColor(R.color.white));// 白色
        }
    }

    @Override
    public int getItemCount() {
        return detailBeans.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTabView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTabView = itemView.findViewById(R.id.tabView);
        }
    }
}

