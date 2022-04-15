package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Stocks {
    @GET("/doc/getStockHSABaseInfo")
    suspend fun getStock(
        @Query("code") code: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<StockResponse>
}

data class StockResponse(
    val code: Int,
    val message: String,
    val data: List<Stock>
)

data class Stock(
    val code: String,// 股票代码
    val name: String,// 股票名称
)