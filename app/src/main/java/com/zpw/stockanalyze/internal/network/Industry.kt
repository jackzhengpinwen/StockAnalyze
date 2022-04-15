package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Industrys {
    @GET("/doc/getStockHyBKBaseInfo")
    suspend fun getIndustry(
        @Query("code") code: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<IndustryResponse>

    @GET("/doc/getStockHYADailyMarket")
    suspend fun getIndustryDetail(
        @Query("code") code: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<IndustryDetailResponse>
}

data class IndustryResponse(
    val code: Int,
    val message: String,
    val data: List<Industry>
)

data class Industry(
    val code: String,// 股票代码
    val name: String,// 股票名称
    val cfg: String,// 成分股
)

data class IndustryDetailResponse(
    val code: Int,
    val message: String,
    val data: List<IndustryDetail>
)

data class IndustryDetail(
    val code: String,// 股票代码
    val name: String,// 股票名称
    val tdate: String,// 交易时间
    val price: Float,// 最新价（元）
    val zdfd: Float,// 涨跌幅度（%）
    val cje: Float,// 成交额（元）
)