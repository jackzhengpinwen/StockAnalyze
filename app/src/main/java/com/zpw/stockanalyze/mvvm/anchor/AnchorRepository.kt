package com.zpw.stockanalyze.mvvm.anchor

import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.utils.DateUtils

class AnchorRepository: BaseRepository() {

    suspend fun getValueData(anchorType: String): List<DailyQuote> {
        return netHelper.getDailyQuotes().getDailyQuotes(
            anchorType,
            "2022-01-01",// TODO 动态替换为当前年份第一天
            DateUtils.getCurrentDay(),
            AssetsUtils.API_ARGS["stockDailyQuotes"]!!,
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}