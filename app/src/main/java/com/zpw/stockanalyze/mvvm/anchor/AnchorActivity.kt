package com.zpw.stockanalyze.mvvm.anchor

import android.widget.Button
import androidx.lifecycle.lifecycleScope
import cn.jingzhuan.lib.chart.Viewport
import cn.jingzhuan.lib.chart.Viewport.*
import cn.jingzhuan.lib.chart.data.LineDataSet
import cn.jingzhuan.lib.chart.data.PointValue
import cn.jingzhuan.lib.chart.widget.LineChart
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityAnchorChartBinding
import com.zpw.stockanalyze.mvvm.utils.AppViewModelFactory
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CountDownLatch

class AnchorActivity: BaseMvvmActivity<LineDataViewModel, ActivityAnchorChartBinding>() {

    lateinit var lineChart: LineChart

    override fun getLayoutId(): Int = R.layout.activity_anchor_chart

    override fun initView() {
        lineChart = findViewById(R.id.lineChart)
        lineChart.setCurrentViewport(Viewport(0.5f, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX))
        lineChart.setDoubleTapToZoom(true)
        findViewById<Button>(R.id.button_value).setOnClickListener {
            viewModel.comeToValue(this)
        }
        findViewById<Button>(R.id.button_grow).setOnClickListener {
            viewModel.comeToGrow(this)
        }
        findViewById<Button>(R.id.button_anchor).setOnClickListener {
            viewModel.comeToAnchor(this)
        }
    }

    override fun getViewModelOrFactory(): Any = AppViewModelFactory(AnchorRepository())

    override fun initObservable(viewModel: LineDataViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: LineDataViewModel) {
        if (viewModel.valueData.isNotEmpty() && viewModel.growData.isNotEmpty() && viewModel.anchorData.isNotEmpty()) return

        val valueDatas: ArrayList<PointValue> = ArrayList()
        val growDatas: ArrayList<PointValue> = ArrayList()
        val datas: ArrayList<PointValue> = ArrayList()
        val countDownLatch = CountDownLatch(3)
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            val valueData = viewModel.getValueData()
            for (i in valueData.indices) {
                if(i == 0) {
                    valueDatas.add(PointValue(500 + 500 * valueData[i] * 0.01f))
                } else {
                    val value = valueDatas.get(i - 1).value
                    valueDatas.add(PointValue(value * (1 + valueData[i] * 0.01f)))
                }
            }
//            val fl = valueData.get(valueData.size - 1) - valueData.get(valueData.size - 2)
//            val change = fl / valueData.get(valueData.size - 2)
//            val decimalFormat = DecimalFormat("0.00") //构造方法的字符格式这里如果小数不足2位,会以0补足.
//            val p: String = decimalFormat.format(change.toDouble()) //format 返回的是字符串
//            Timber.d("valueData is ${ p }")
            countDownLatch.countDown()
        }

        lifecycleScope.async(Dispatchers.IO) {
            val growData = viewModel.getGrowData()
            for (i in growData.indices) {
                if(i == 0) {
                    growDatas.add(PointValue((500 + 500 * growData[i] * 0.01f)))
                } else {
                    val value2 = growDatas.get(i - 1).value
                    growDatas.add(PointValue(value2 * (1 + growData[i] * 0.01f)))
                }
            }
            Timber.d("growData countDown")
            countDownLatch.countDown()
        }

        lifecycleScope.async(Dispatchers.IO) {
            val data = viewModel.getData()
            for (i in data.indices) {
                if(i == 0) {
                    datas.add(PointValue((500 + 500 * data[i] * 0.01f)))
                } else {
                    val value3 = datas.get(i - 1).value
                    datas.add(PointValue(value3 * (1 + data[i] * 0.01f)))
                }
            }
            Timber.d("data countDown")
            countDownLatch.countDown()
        }

        lifecycleScope.async(Dispatchers.IO) {
            countDownLatch.await()
            launch(Dispatchers.Main) {
                loadingDialog.dismiss()
                val line = LineDataSet(valueDatas)
                line.color = resources.getColor(R.color.redColor, theme)
                lineChart.addLine(line)
                val line2 = LineDataSet(growDatas)
                lineChart.addLine(line2)
                line2.color = resources.getColor(R.color.blueColor, theme)
                val line3 = LineDataSet(datas)
                lineChart.addLine(line3)
                line3.color = resources.getColor(R.color.backColor, theme)
                lineChart.invalidate()
            }
        }
    }
}