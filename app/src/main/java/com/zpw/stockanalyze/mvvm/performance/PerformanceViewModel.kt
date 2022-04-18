package com.zpw.stockanalyze.mvvm.performance

import com.zpw.stockanalyze.internal.network.CW
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class PerformanceViewModel(
    private val repo: PerformanceRepository
    ): BaseViewModel<PerformanceRepository>(repo) {
    private val TAG: String = PerformanceViewModel::class.java.simpleName

    suspend fun getCWDetail(code: String): List<CW> {
        val cws = repo.getCWDetail(code)
        return cws
    }
}