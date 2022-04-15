package com.zpw.stockanalyze.mvvm.search

import com.zpw.stockanalyze.internal.network.CW
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.network.Stock
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.performance.PerformanceRepository

class SearchRepository(val netHelper: NetHelper): BaseRepository() {
    private val TAG: String = PerformanceRepository::class.java.simpleName

    suspend fun getStock(): List<Stock> {
        return netHelper.getStock().getStock(
            "all",
            "code,name",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}