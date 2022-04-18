package com.zpw.stockanalyze.internal.network

import com.zpw.stockanalyze.App
import com.zpw.stockanalyze.internal.utils.AppGlobal
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

class NetHelper {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface NetHelperInterface {
        fun getService(): Retrofit
    }

    private val retrofit: Retrofit = EntryPointAccessors.fromApplication(
        AppGlobal.getApplication(),
        NetHelperInterface::class.java
    ).getService()

    fun getDailyQuotes(): DailyQuotes = retrofit.create(DailyQuotes::class.java)

    fun getFutures(): Futures = retrofit.create(Futures::class.java)

    fun getIndustry(): Industrys = retrofit.create(Industrys::class.java)

    fun getCW(): CWs = retrofit.create(CWs::class.java)

    fun getStock(): Stocks = retrofit.create(Stocks::class.java)

    fun getStockValue(): StockValues = retrofit.create(StockValues::class.java)
}