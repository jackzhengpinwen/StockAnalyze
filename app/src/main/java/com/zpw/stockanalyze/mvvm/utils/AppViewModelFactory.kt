package com.zpw.stockanalyze.mvvm.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.mvvm.dailyquotes.DailyQuotesRepository
import com.zpw.stockanalyze.mvvm.anchor.AnchorRepository
import com.zpw.stockanalyze.mvvm.futures.FuturesRepository
import com.zpw.stockanalyze.mvvm.industry.IndustryRepository
import com.zpw.stockanalyze.mvvm.menu.MenuRepository
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.performance.PerformanceRepository
import com.zpw.stockanalyze.mvvm.search.SearchRepository

internal class AppViewModelFactory (val repository: BaseRepository) : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repository::class.java)
            .newInstance(repository)
    }
}