package com.zpw.stockanalyze.mvvm.industry.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zpw.stockanalyze.R;
import com.zpw.stockanalyze.internal.network.Industry;
import com.zpw.stockanalyze.internal.network.IndustryDetail;
import com.zpw.stockanalyze.mvvm.dailyquotes.DailyQuotesActivity;
import com.zpw.stockanalyze.mvvm.dailyquotes.view.CustomizeScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    /**
     * 保存列表ViewHolder集合
     */
    private List<ViewHolder> recyclerViewHolder = new ArrayList<>();
    /**
     * 记录item滑动的位置，用于RecyclerView上下滚动时更新所有列表
     */
    private int offestX;

    private OnTabScrollViewListener onTabScrollViewListener;

    private Industry[] industry;
    private List<IndustryDetail>[] industryDetail;
    private Boolean analyze;

    private Activity activity;

    public StockAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setOnTabScrollViewListener(OnTabScrollViewListener onTabScrollViewListener) {
        this.onTabScrollViewListener = onTabScrollViewListener;
    }

    public void setIndustrys(Map<Industry, List<IndustryDetail>> industrys, Boolean analyze) {
        Industry[] industry = new Industry[industrys.size()];
        this.industry = industrys.keySet().toArray(industry);
        List<IndustryDetail>[] industryDetail = new ArrayList[industrys.size()];
        this.industryDetail = industrys.values().toArray(industryDetail);
        this.analyze = analyze;
        notifyDataSetChanged();
    }

    public List<ViewHolder> getRecyclerViewHolder() {
        return recyclerViewHolder;
    }

    public int getOffestX() {
        return offestX;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_dailyquotes_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mStockName.setText(industry[position].getName());
        holder.mStockRecyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        holder.mStockRecyclerView.setNestedScrollingEnabled(false);

        holder.mStockName.setOnClickListener((view) -> {
            String cfg = industry[position].getCfg().replace("_", ",");
            Intent intent = new Intent(activity, DailyQuotesActivity.class);
            intent.putExtra(DailyQuotesActivity.CFG, cfg);
            intent.putExtra(DailyQuotesActivity.ANALYZE, analyze);
            activity.startActivity(intent);
        });
        // TODO：文本RecyclerView中具体信息的RecyclerView（RecyclerView嵌套）
        StockItemAdapter stockItemAdapter = new StockItemAdapter(activity);
        holder.mStockRecyclerView.setAdapter(stockItemAdapter);

        List<String> detailBeans = new ArrayList<>();
        List<IndustryDetail> industryDetails = this.industryDetail[position];

        IndustryDetail curIndustryDetail = industryDetails.get(industryDetails.size() - 1);
        IndustryDetail yearIndustryDetail = industryDetails.get(0);
        // 当日涨跌幅
        String zdf = String.valueOf(curIndustryDetail.getZdfd());
        detailBeans.add(zdf);
        // 今年涨幅
        float curPrice = curIndustryDetail.getPrice();
        float yearPrice = yearIndustryDetail.getPrice();
        float price = (curPrice - yearPrice) / yearPrice * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);//format 返回的是字符串
        detailBeans.add(p);
        // 成交额
        float cje = 0f;
        for (IndustryDetail detail : industryDetails) {
            cje += detail.getCje();
        }
        float s = cje / 100_000_000;
        p = decimalFormat.format(s);//format 返回的是字符串
        String cjeStr = String.format("%s亿", p);
        detailBeans.add(cjeStr);
        // 兼容框架
        stockItemAdapter.setDetailBeans(detailBeans);

        if (!recyclerViewHolder.contains(holder)) {
            recyclerViewHolder.add(holder);
        }

        /**
         * 第一步：水平滑动item时，遍历所有ViewHolder，使得整个列表的HorizontalScrollView同步滚动
         */
        holder.mStockScrollView.setViewListener(new CustomizeScrollView.OnScrollViewListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                for (ViewHolder viewHolder : recyclerViewHolder) {
                    if (viewHolder != holder) {
                        viewHolder.mStockScrollView.scrollTo(l, 0);
                    }
                }
                /**
                 * 第二步：水平滑动item时，接口回调到Tab栏的HorizontalScrollView，使得Tab栏跟随item滚动实时更新
                 */
                if (onTabScrollViewListener != null) {
                    onTabScrollViewListener.scrollTo(l, t);
                    offestX = l;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return industry != null ? (industry.length == 0 ? 0 : industry.length) : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mStockName;
        public CustomizeScrollView mStockScrollView;
        public RecyclerView mStockRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStockName = itemView.findViewById(R.id.stockName);
            mStockScrollView = itemView.findViewById(R.id.stockScrollView);
            mStockRecyclerView = itemView.findViewById(R.id.stockRecyclerView);
        }
    }

    public interface OnTabScrollViewListener {
        void scrollTo(int l, int t);
    }
}
