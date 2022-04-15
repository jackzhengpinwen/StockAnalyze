package com.zpw.stockanalyze.mvvm.dailyquotes.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class CustomizeScrollView extends HorizontalScrollView {

    private OnScrollViewListener viewListener;

    public interface OnScrollViewListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }

    public void setViewListener(OnScrollViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public CustomizeScrollView(Context context) {
        this(context,null);
    }

    public CustomizeScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomizeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (viewListener != null) {
            viewListener.onScroll(l, t, oldl, oldt);
        }
    }

}
