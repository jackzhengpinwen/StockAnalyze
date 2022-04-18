package com.zpw.stockanalyze.mvvm.menu

import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityMenuBinding
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.utils.AppViewModelFactory
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MenuActivity: BaseMvvmActivity<MenuViewModel, ActivityMenuBinding>() {
    lateinit var overview: TextView

    override fun getLayoutId(): Int = R.layout.activity_menu

    override fun initView() {
        overview = findViewById(R.id.overview)
        findViewById<TextView>(R.id.search).setOnClickListener {
            viewModel.comeToSearch(this)
        }
        findViewById<TextView>(R.id.anchor).setOnClickListener {
            viewModel.comeToAnchorValue(this)
        }
        findViewById<TextView>(R.id.future).setOnClickListener {
            viewModel.comeToFutures(this)
        }
        findViewById<TextView>(R.id.industry).setOnClickListener {
            viewModel.comeToIndustry(this)
        }
        findViewById<TextView>(R.id.analyze).setOnClickListener {
            viewModel.comeToIndustry(this, true)
        }
    }

    override fun getViewModelOrFactory(): Any = AppViewModelFactory(MenuRepository())

    override fun initObservable(viewModel: MenuViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: MenuViewModel) {
        if (viewModel.stockValue.isNotEmpty()) return
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            val stock = viewModel.getStockValue()
            lifecycleScope.launch(Dispatchers.Main) {
                val ratio = stock.hszsz / AssetsUtils.GDP * 100
                val decimalFormat = DecimalFormat("0.00")
                val format = decimalFormat.format(ratio)
                var evaluate = ""
                if (ratio >= 130) {
                    evaluate = "（看空）"
                } else if (ratio >= 100 && ratio < 130) {
                    evaluate = "（注意风险）"
                } else if (ratio >= 70 && ratio < 100) {
                    evaluate = "（买入）"
                } else if (ratio >= 50 && ratio < 70) {
                    evaluate = "（加杠杆）"
                } else if (ratio >= 30 && ratio < 50) {
                    evaluate = "（抵押房子）"
                } else if (ratio >= 0 && ratio < 30) {
                    evaluate = "（卖身）"
                }
                overview.setText("" +
                        "日期：${stock.sdate}\n"+
                        "新增投资者数量（万户）:${stock.xzsl}\n" +
                        "期末投资者总量（万户）:${stock.qmsl}\n" +
                        "沪深总市值（亿元）:${stock.hszsz}\n" +
                        "上年GDP（亿元）：${AssetsUtils.GDP}\n" +
                        "资产证券化率（%）：${format}" + evaluate
                )
                loadingDialog.dismiss()
            }
        }
    }
}