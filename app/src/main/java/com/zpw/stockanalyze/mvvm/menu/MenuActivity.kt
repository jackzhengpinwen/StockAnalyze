package com.zpw.stockanalyze.mvvm.menu

import android.widget.TextView
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivityMenuBinding
import com.zpw.stockanalyze.mvvm.utils.AppInjection
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity

class MenuActivity: BaseMvvmActivity<MenuViewModel, ActivityMenuBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_menu

    override fun initView() {
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

    override fun getViewModelOrFactory(): Any = AppInjection.getMenuViewModelFactory()

    override fun initObservable(viewModel: MenuViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: MenuViewModel) {

    }
}