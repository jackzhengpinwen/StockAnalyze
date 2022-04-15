package com.zpw.stockanalyze.mvvm.industry

import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.network.Industry
import com.zpw.stockanalyze.internal.network.IndustryDetail
import com.zpw.stockanalyze.mvvm.utils.AppExecutors
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class IndustryViewModel(
    private val repo: IndustryRepository,
    private val executors: AppExecutors
) : BaseViewModel<IndustryRepository>(repo) {
    private val TAG: String = IndustryViewModel::class.java.simpleName
    var industryData: List<Industry> = emptyList()
    var industryDetailData: List<IndustryDetail> = emptyList()

    suspend fun getIndustryData(): List<Industry> {
        industryData = repo.getValueData()
        return industryData
    }

    suspend fun getIndustryDetailData(industry: String): List<IndustryDetail> {
        industryDetailData = repo.getDetailDataindustry(industry)
        return industryDetailData
    }
}