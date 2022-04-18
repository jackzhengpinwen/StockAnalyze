package com.zpw.stockanalyze.mvvm.menu

import android.content.Intent
import com.zpw.stockanalyze.internal.network.StockValue
import com.zpw.stockanalyze.mvvm.anchor.AnchorActivity
import com.zpw.stockanalyze.mvvm.futures.FuturesActivity
import com.zpw.stockanalyze.mvvm.industry.IndustryActivity
import com.zpw.stockanalyze.mvvm.search.SearchActivity
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class MenuViewModel(
    private val repo: MenuRepository
): BaseViewModel<MenuRepository>(repo) {
    private val TAG: String = MenuViewModel::class.java.simpleName
    val stockValue = mutableListOf<StockValue>()

    fun comeToSearch(activity: MenuActivity) {
        activity.startActivity(Intent(activity, SearchActivity::class.java))
    }

    fun comeToAnchorValue(activity: MenuActivity) {
        activity.startActivity(Intent(activity, AnchorActivity::class.java))
    }

    fun comeToFutures(activity: MenuActivity) {
        activity.startActivity(Intent(activity, FuturesActivity::class.java))
    }

    fun comeToIndustry(activity: MenuActivity, analyze: Boolean? = false) {
        val intent = Intent(activity, IndustryActivity::class.java)
        intent.putExtra(IndustryActivity.ANALYZE, analyze)
        activity.startActivity(intent)
    }

    suspend fun getStockValue(): StockValue {
        stockValue.addAll(repo.getStockValue())
        val data = stockValue[stockValue.size - 1]
        return data
    }
}