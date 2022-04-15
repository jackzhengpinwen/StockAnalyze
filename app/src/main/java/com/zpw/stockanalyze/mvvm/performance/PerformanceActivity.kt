package com.zpw.stockanalyze.mvvm.performance

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityPerformanceBinding
import com.zpw.stockanalyze.internal.network.CW
import com.zpw.stockanalyze.mvvm.dailyquotes.view.CustomizeScrollView
import com.zpw.stockanalyze.mvvm.performance.adapter.StockAdapter
import com.zpw.stockanalyze.mvvm.performance.adapter.TabAdapter
import com.zpw.stockanalyze.mvvm.utils.AppInjection
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class PerformanceActivity: BaseMvvmActivity<PerformanceViewModel, ActivityPerformanceBinding>(),
    StockAdapter.OnTabScrollViewListener {

    companion object {
        @JvmField
        var CODE: String = ""
    }

    /**
     * Tab栏ScrollView
     */
    lateinit var headHorizontalScrollView: CustomizeScrollView

    /**
     * Tab栏RecyclerView
     */
    lateinit var mHeadRecyclerView: RecyclerView

    /**
     * 列表RecyclerView
     */
    lateinit var mContentRecyclerView: RecyclerView

    /**
     * Tab栏Adapter
     */
    lateinit var mTabAdapter: TabAdapter

    /**
     * 列表Adapter
     */
    lateinit var mStockAdapter: StockAdapter

    /**
     * Tab栏标题
     */
    var values = arrayOf("日期","营业同比", "营业环比", "利润同比", "利润环比", "毛利率", "净利率")

    lateinit var cws: List<CW>

    lateinit var dataType: String

    override fun getLayoutId(): Int = R.layout.activity_performance

    override fun initView() {
        dataType = intent.extras?.getString(CODE) ?: ""
        Timber.d("dataType is ${dataType}")

        mHeadRecyclerView = findViewById(R.id.headRecyclerView)
        mContentRecyclerView = findViewById(R.id.contentRecyclerView)
        headHorizontalScrollView = findViewById(R.id.headScrollView)

        // 设置RecyclerView水平显示
        mHeadRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mTabAdapter = TabAdapter(this)
        // 设置ListView禁止滑动，这样使得ScrollView滑动更流畅
        mHeadRecyclerView.isNestedScrollingEnabled = false
        mHeadRecyclerView.adapter = mTabAdapter
        initTabData()


        // 设置股票列表
        mContentRecyclerView.layoutManager = LinearLayoutManager(this)
        mStockAdapter = StockAdapter(this)
        mContentRecyclerView.adapter = mStockAdapter
        mStockAdapter.setOnTabScrollViewListener(this)
        initStockData()

        initListener()
    }

    /**
     * 初始化Tab数据
     */
    fun initTabData() {
        mTabAdapter.setTabData(Arrays.asList(*values))
    }

    /**
     * 初始化列表数据
     */
    fun initStockData() {

    }

    private fun initListener() {
        /**
         * 第三步：Tab栏HorizontalScrollView水平滚动时，遍历所有RecyclerView列表，并使其跟随滚动
         */
        headHorizontalScrollView.setViewListener(object : CustomizeScrollView.OnScrollViewListener {
            override fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int) {
                val viewHolders = mStockAdapter.recyclerViewHolder
                for (viewHolder in viewHolders) {
                    viewHolder.mStockScrollView.scrollTo(l, 0)
                }
            }
        })

        /**
         * 第四步：RecyclerView垂直滑动时，遍历更新所有item中HorizontalScrollView的滚动位置，否则会出现item位置未发生变化状态
         */
        mContentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val viewHolders = mStockAdapter.recyclerViewHolder
                for (viewHolder in viewHolders) {
                    viewHolder.mStockScrollView.scrollTo(mStockAdapter.offestX, 0)
                }
            }
        })
    }

    override fun getViewModelOrFactory(): Any = AppInjection.getPerformanceViewModelFactory()

    override fun initObservable(viewModel: PerformanceViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: PerformanceViewModel) {
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            cws = viewModel.getCWDetail(dataType ?: "")
            Timber.d("cws is ${cws}")
            lifecycleScope.launch(Dispatchers.Main) {
                mStockAdapter.setCws(cws)
                loadingDialog.dismiss()
            }
        }
    }

    override fun scrollTo(l: Int, t: Int) {
        if (headHorizontalScrollView != null) {
            headHorizontalScrollView.scrollTo(l, 0)
        }
    }
}