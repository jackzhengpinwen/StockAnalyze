package com.zpw.stockanalyze.mvvm.dailyquotes

import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class DailyQuotesViewModel(
    private val repo: DailyQuotesRepository
    ): BaseViewModel<DailyQuotesRepository>(repo) {
    private val TAG: String = DailyQuotesViewModel::class.java.simpleName

    val newQuotes = mutableListOf<DailyQuote>()
    val dailyQuotes = mutableListOf<DailyQuote>()

    suspend fun getDailyQuotes(dataType: String, analyze: Boolean? = false): List<DailyQuote> {
        dailyQuotes.addAll(repo.getDailyQuotes(dataType))
        if(analyze == false) {
            return dailyQuotes
        }
        dailyQuotes.forEach {
            getCWDetail(it)
        }
        return newQuotes
    }

    suspend fun getCWDetail(dailyQuote: DailyQuote) {
        val cwDetail = repo.getCWDetail(dailyQuote.code)
        val cw = cwDetail[cwDetail.size - 1]
        if(cw.yyzsrgdhbzc > 10 && cw.kfjlrgdhbzc > 10 && cw.totaloperaterevetz > 20 && cw.kcfjcxsyjlrtz > 20) {
            newQuotes.add(dailyQuote)
        }
    }
}