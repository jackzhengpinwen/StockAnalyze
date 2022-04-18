package com.zpw.stockanalyze.mvvm.search

import android.content.Intent
import com.zpw.stockanalyze.internal.network.Stock
import com.zpw.stockanalyze.mvvm.performance.PerformanceActivity
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class SearchViewModel(
    private val repo: SearchRepository
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