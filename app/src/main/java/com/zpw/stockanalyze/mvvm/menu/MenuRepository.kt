package com.zpw.stockanalyze.mvvm.menu

import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.network.Stock
import com.zpw.stockanalyze.internal.network.StockValue
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.performance.PerformanceRepository

class MenuRepository(val netHelper: NetHelper): BaseRepository() {
    private val TAG: String = MenuRepository::class.java.simpleName

    suspend fun getStockValue(): List<StockValue> {
        return netHelper.getStockValue().getStockValue(
            "sdate,xzsl,qmsl,hszsz",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}