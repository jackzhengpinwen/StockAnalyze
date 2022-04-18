package com.zpw.stockanalyze.mvvm.industry

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityIndustryBinding
import com.zpw.stockanalyze.internal.network.Industry
import com.zpw.stockanalyze.internal.network.IndustryDetail
import com.zpw.stockanalyze.mvvm.dailyquotes.view.CustomizeScrollView
import com.zpw.stockanalyze.mvvm.industry.adapter.StockAdapter
import com.zpw.stockanalyze.mvvm.industry.adapter.TabAdapter
import com.zpw.stockanalyze.mvvm.utils.AppViewModelFactory
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.concurrent.CountDownLatch

class IndustryActivity : BaseMvvmActivity<IndustryViewModel, ActivityIndustryBinding>(),
    StockAdapter.OnTabScrollViewListener {

    companion object {
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
     * 列表Adapter
     */
    lateinit var mStockAdapter: StockAdapter

    /**
     * Tab栏Adapter
     */
    lateinit var mTabAdapter: TabAdapter

    /**
     * Tab栏标题
     */
    var values = arrayOf("涨跌幅", "今年涨幅", "今年成交", "成交占比")

    var analyze: Boolean? = false

    override fun getLayoutId(): Int = R.layout.activity_industry

    override fun initView() {
        analyze = intent.extras?.getBoolean(ANALYZE)

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

    override fun getViewModelOrFactory(): Any = AppViewModelFactory(IndustryRepository())

    override fun initObservable(viewModel: IndustryViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: IndustryViewModel) {
        if (viewModel.industryData.isNotEmpty() && viewModel.industryDetailData.isNotEmpty()) return
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            val industryDatas = mutableMapOf<Industry, List<IndustryDetail>>()
            // 获取板块信息
            val industry = viewModel.getIndustryData()
            Timber.d("industry is ${industry}")
            val countDownLatch = CountDownLatch(industry.size)
            // 获取板块详细信息
            industry.forEach {
                lifecycleScope.async(Dispatchers.IO) {
                    val industryDetailData = viewModel.getIndustryDetailData(it.code)
                    industryDatas[it] = industryDetailData
                    countDownLatch.countDown()
                }
            }
            Timber.d("countDownLatch is wait")
            countDownLatch.await()
            lifecycleScope.launch(Dispatchers.Main) {
                mStockAdapter.setIndustrys(industryDatas, analyze)
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