package com.zpw.stockanalyze.mvvm.futures

import androidx.lifecycle.lifecycleScope
import cn.jingzhuan.lib.chart.Viewport
import cn.jingzhuan.lib.chart.Viewport.*
import cn.jingzhuan.lib.chart.data.LineDataSet
import cn.jingzhuan.lib.chart.data.PointValue
import cn.jingzhuan.lib.chart.widget.LineChart
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityFuturesChartBinding
import com.zpw.stockanalyze.mvvm.utils.AppInjection
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class FuturesActivity: BaseMvvmActivity<FuturesViewModel, ActivityFuturesChartBinding>() {

    lateinit var lineChart: LineChart

    override fun getLayoutId(): Int = R.layout.activity_futures_chart

    override fun initView() {
        lineChart = findViewById(R.id.lineChart)
        lineChart.setCurrentViewport(Viewport(0.5f, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX))
        lineChart.setDoubleTapToZoom(true)
    }

    override fun getViewModelOrFactory(): Any = AppInjection.getFuturesViewModelFactory()

    override fun initObservable(viewModel: FuturesViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: FuturesViewModel) {
        val valueDatas: ArrayList<PointValue> = ArrayList()
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async {
            val imFuturesData = viewModel.getIMFuturesData()
            imFuturesData.forEach {
                valueDatas.add(PointValue(it))
            }
            Timber.d("imFuturesData is ${imFuturesData}")
            launch(Dispatchers.Main) {
                val line = LineDataSet(valueDatas)
                line.color = resources.getColor(R.color.redColor, theme)
                lineChart.addLine(line)
                loadingDialog.dismiss()
            }
        }
    }
}