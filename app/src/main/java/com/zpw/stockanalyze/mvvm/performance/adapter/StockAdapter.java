package com.zpw.stockanalyze.mvvm.performance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zpw.stockanalyze.R;
import com.zpw.stockanalyze.internal.network.CW;
import com.zpw.stockanalyze.mvvm.dailyquotes.view.CustomizeScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    private CW cws;

    private Context mContext;

    public StockAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnTabScrollViewListener(OnTabScrollViewListener onTabScrollViewListener) {
        this.onTabScrollViewListener = onTabScrollViewListener;
    }

    public void setCws(List<CW> cws) {
        if (cws.isEmpty()) return;
        this.cws = cws.get(cws.size() - 1);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dailyquotes_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mStockName.setText(cws.getName());
        holder.mStockRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        holder.mStockRecyclerView.setNestedScrollingEnabled(false);

        // TODO：文本RecyclerView中具体信息的RecyclerView（RecyclerView嵌套）
        StockItemAdapter stockItemAdapter = new StockItemAdapter(mContext);
        holder.mStockRecyclerView.setAdapter(stockItemAdapter);

        // 兼容框架
        CW cw = cws;
        List<String> detail = new ArrayList<>();
        detail.add(cw.getTdate());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        detail.add(decimalFormat.format(cw.getTotaloperaterevetz()));
        detail.add(decimalFormat.format(cw.getYyzsrgdhbzc()));
        detail.add(decimalFormat.format(cw.getKcfjcxsyjlrtz()));
        detail.add(decimalFormat.format(cw.getKfjlrgdhbzc()));
        detail.add(decimalFormat.format(cw.getXsmll()));
        detail.add(decimalFormat.format(cw.getXsjll()));
        stockItemAdapter.setDetailBeans(detail);

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
        return cws != null ? 1 : 0;
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
