package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockValues {
    @GET("/doc/getStockAccount")
    suspend fun getStockValue(
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<StockValueResponse>
}

data class StockValueResponse(
    val code: Int,
    val message: String,
    val data: List<StockValue>
)

data class StockValue(
    val sdate: String,// 数据日期
    val xzsl: Float,// 新增投资者数量（万户）
    val qmsl: String,// 期末投资者总量（万户）
    val hszsz: Float,// 沪深总市值（亿元）
)