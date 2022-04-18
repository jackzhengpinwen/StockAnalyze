package com.zpw.stockanalyze.mvvm.performance

import com.zpw.stockanalyze.internal.network.CW
import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.utils.DateUtils

class PerformanceRepository: BaseRepository() {
    private val TAG: String = PerformanceRepository::class.java.simpleName

    suspend fun getCWDetail(code: String): List<CW> {
        return netHelper.getCW().getCW(
            code,
            "0",
            "2000-01-01",
            "2100-12-31",
            "code,name,tdate,kcfjcxsyjlrtz,kfjlrgdhbzc,xsmll,xsjll,totaloperaterevetz,yyzsrgdhbzc",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}