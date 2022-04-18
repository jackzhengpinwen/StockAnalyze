package com.zpw.stockanalyze.mvvm.futures

import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.network.Future
import com.zpw.stockanalyze.internal.network.Futures
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.utils.DateUtils

class FuturesRepository: BaseRepository() {

    suspend fun getValueData(futureType: String): List<Future> {
        return netHelper.getFutures().getFutures(
            futureType,
            "2022-01-01",// TODO 动态替换为当前年份第一天
            DateUtils.getCurrentDay(),
            AssetsUtils.API_ARGS["futureDailyQuotes"]!!,
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}