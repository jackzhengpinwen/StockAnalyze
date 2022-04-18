package com.zpw.stockanalyze.mvvm.dailyquotes

import com.zpw.stockanalyze.internal.network.CW
import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.utils.AppGlobal
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.utils.DateUtils

class DailyQuotesRepository: BaseRepository() {
    private val TAG: String = DailyQuotesRepository::class.java.simpleName

    suspend fun getDailyQuotes(dataType: String): List<DailyQuote> {
        return netHelper.getDailyQuotes().getDailyQuotes(
            dataType,
            DateUtils.getCurrentDay(),
            DateUtils.getCurrentDay(),
            AssetsUtils.API_ARGS["stockDailyQuotes"]!!,
            "1",
            AssetsUtils.TOKEN
        ).body()?.data?.distinctBy { it.code } ?: emptyList()
    }

    suspend fun getCWDetail(code: String): List<CW> {
        return netHelper.getCW().getCW(
            code,
            "0",
            "2000-01-01",
            "2100-12-31",
            "code,name,tdate,kcfjcxsyjlrtz,kfjlrgdhbzc,xsmll,xsjll,totaloperaterevetz, yyzsrgdhbzc",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}