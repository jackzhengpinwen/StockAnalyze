package com.zpw.stockanalyze.internal.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CWs {
    @GET("/doc/getCaiWuZYZBReportHSA")
    suspend fun getCW(
        @Query("code") code: String,
        @Query("mtype") mtype: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("fields") fields: String,
        @Query("export") export: String,
        @Query("token") token: String,
    ): Response<CWResponse>
}

data class CWResponse(
    val code: Int,
    val message: String,
    val data: List<CW>
)

data class CW(
    val code: String,// 股票代码
    val name: String,// 股票名称
    val tdate: String,// 报告日期
    val totaloperaterevetz: Float,// 营业总收入同比增长(%)
    val yyzsrgdhbzc: Float,// 营业总收入滚动环比增长(%)
    val kcfjcxsyjlrtz: Float,// 扣非净利润同比增长(%)
    val kfjlrgdhbzc: Float,// 扣非净利润滚动环比增长(%)
    val xsmll: Float,// 毛利率(%)
    val xsjll: Float,// 净利率(%)
)