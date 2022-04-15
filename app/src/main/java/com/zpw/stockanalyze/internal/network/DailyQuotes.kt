package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DailyQuotes {
    @GET("/doc/getStockHSADailyMarket")
    suspend fun getDailyQuotes(
        @Query("code") code: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<DailyQuotesResponse>
}

data class DailyQuotesResponse(
    val code: Int,
    val message: String,
    val data: List<DailyQuote>
)

data class DailyQuote(
    val code: String,// 股票代码
    val name: String,// 股票名称
    val tdate: String,// 交易时间
    val price: String,// 股票价格
    val jrkpj: String,// 今日开盘价
    val zrspj: String,// 昨日收盘价
    val zgj: String,// 最高价
    val zdj: String,// 最低价
    val zdfd: String,// 股票涨跌幅
    val sjlv: String,// 股票市盈率
    val dsyl: String,// 股票市净率
    val ltsz: Double,// 股票流通市值
)