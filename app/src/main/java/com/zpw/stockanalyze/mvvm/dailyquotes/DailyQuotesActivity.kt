package com.zpw.stockanalyze.mvvm.dailyquotes

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityDailyquotesBinding
import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.mvvm.dailyquotes.adapter.StockAdapter
import com.zpw.stockanalyze.mvvm.dailyquotes.adapter.TabAdapter
import com.zpw.stockanalyze.mvvm.dailyquotes.view.CustomizeScrollView
import com.zpw.stockanalyze.mvvm.utils.AppViewModelFactory
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class DailyQuotesActivity: BaseMvvmActivity<DailyQuotesViewModel, ActivityDailyquotesBinding>(),
    StockAdapter.OnTabScrollViewListener {

    companion object {
        @JvmField
        var CFG: String = "CFG"
        var DATA_TYPE = "DATA_TYPE"
        @JvmField
        var ANALYZE = "ANALYZE"
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
    var values = arrayOf("最新", "涨幅", "市盈率", "市净率", "流通市值")

    lateinit var dailyQuotes: List<DailyQuote>

    lateinit var dataType: String
    var analyze: Boolean? = false

    override fun getLayoutId(): Int = R.layout.activity_dailyquotes

    override fun initView() {
        dataType = intent.extras?.getString(DATA_TYPE) ?: ""
        if (dataType.isEmpty()) {
            dataType = intent.extras?.getString(CFG) ?: ""
        }
        analyze = intent.extras?.getBoolean(ANALYZE) ?: false

        Timber.d("dataType is ${dataType}")
        Timber.d("analyze is ${analyze}")

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

    override fun getViewModelOrFactory(): Any = AppViewModelFactory(DailyQuotesRepository())

    override fun initObservable(viewModel: DailyQuotesViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: DailyQuotesViewModel) {
        if (viewModel.dailyQuotes.isNotEmpty()) return;
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            dailyQuotes = viewModel.getDailyQuotes(dataType ?: "", analyze)
            Timber.d("dailyQuotes is ${dailyQuotes}")
            lifecycleScope.launch(Dispatchers.Main) {
                mStockAdapter.setDailyQuotes(dailyQuotes)
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