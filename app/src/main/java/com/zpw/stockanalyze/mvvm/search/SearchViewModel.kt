package com.zpw.stockanalyze.mvvm.search

import android.content.Intent
import com.zpw.stockanalyze.internal.network.Industry
import com.zpw.stockanalyze.internal.network.Stock
import com.zpw.stockanalyze.mvvm.anchor.AnchorActivity
import com.zpw.stockanalyze.mvvm.futures.FuturesActivity
import com.zpw.stockanalyze.mvvm.industry.IndustryActivity
import com.zpw.stockanalyze.mvvm.performance.PerformanceActivity
import com.zpw.stockanalyze.mvvm.utils.AppExecutors
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class SearchViewModel(
    private val repo: SearchRepository,
    private val executors: AppExecutors
): BaseViewModel<SearchRepository>(repo) {
    private val TAG: String = SearchViewModel::class.java.simpleName
    var stock: List<Stock> = emptyList()

    fun search(activity: SearchActivity, stock: String) {
        val intent = Intent(activity, PerformanceActivity::class.java)
        intent.putExtra(PerformanceActivity.CODE, stock)
        activity.startActivity(intent)
    }

    suspend fun getStock(): List<Stock> {
        stock = repo.getStock()
        return stock
    }
}