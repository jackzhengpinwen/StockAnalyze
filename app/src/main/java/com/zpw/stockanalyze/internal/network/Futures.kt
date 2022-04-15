package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Futures {
    @GET("/doc/getQihuoDailyMarket")
    suspend fun getFutures(
        @Query("code") code: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<FuturesResponse>
}

data class FuturesResponse(
    val code: Int,
    val message: String,
    val data: List<Future>
)

data class Future(
    val jyscode: Int,// 交易所代码
    val jysname: String,// 交易所名称
    val pzcode: String,// 品种代码
    val code: String,// 合约代码
    val name: String,// 合约名称
    val tdate: String,// 交易时间
    val zxj: Float,// 最新价
    val zdf: Float,// 涨跌幅
    val zg: Float,// 最高
    val zd: Float,// 最低
)