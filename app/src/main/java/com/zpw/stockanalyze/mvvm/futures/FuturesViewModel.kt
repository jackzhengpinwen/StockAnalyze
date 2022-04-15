package com.zpw.stockanalyze.mvvm.futures

import com.zpw.stockanalyze.mvvm.utils.AppExecutors
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class FuturesViewModel(
    private val repo: FuturesRepository,
    private val executors: AppExecutors
) : BaseViewModel<FuturesRepository>(repo) {
    private val TAG: String = FuturesViewModel::class.java.simpleName
    val imFutureData = mutableListOf<Float>()

    /**
     * 铁矿石
     */
    suspend fun getIMFuturesData(): List<Float> {
        val imData = repo.getValueData("jm")
        imData.forEach {
            imFutureData.add(it.zxj)
        }
        return imFutureData
    }
}