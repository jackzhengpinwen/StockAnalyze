package com.zpw.stockanalyze.mvvm.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zpw.stockanalyze.mvvm.dailyquotes.DailyQuotesRepository
import com.zpw.stockanalyze.mvvm.anchor.AnchorRepository
import com.zpw.stockanalyze.mvvm.futures.FuturesRepository
import com.zpw.stockanalyze.mvvm.industry.IndustryRepository
import com.zpw.stockanalyze.mvvm.menu.MenuRepository
import com.zpw.stockanalyze.mvvm.performance.PerformanceRepository
import com.zpw.stockanalyze.mvvm.search.SearchRepository

internal class DailyQuotesViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DailyQuotesRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getDailyQuotesRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class MenuViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MenuRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getMenuRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class AnchorViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AnchorRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getAnchorRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class FuturesViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FuturesRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getFuturesRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class IndustryViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IndustryRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getIndustryRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class PerformanceViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PerformanceRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getPerformanceRepository(), AppExecutors.APP_EXECUTORS)
    }
}

internal class SearchViewModelFactory : ViewModelProvider.Factory {
    // 创建 viewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchRepository::class.java, AppExecutors::class.java)
            .newInstance(AppInjection.getSearchRepository(), AppExecutors.APP_EXECUTORS)
    }
}