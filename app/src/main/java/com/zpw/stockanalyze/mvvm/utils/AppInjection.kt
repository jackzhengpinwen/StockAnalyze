package com.zpw.stockanalyze.mvvm.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.mvvm.dailyquotes.DailyQuotesRepository
import com.zpw.stockanalyze.mvvm.anchor.AnchorRepository
import com.zpw.stockanalyze.mvvm.futures.FuturesRepository
import com.zpw.stockanalyze.mvvm.industry.IndustryRepository
import com.zpw.stockanalyze.mvvm.menu.MenuRepository
import com.zpw.stockanalyze.mvvm.performance.PerformanceRepository
import com.zpw.stockanalyze.mvvm.search.SearchRepository

object AppInjection {

    private val dailyQuotesViewModelFactory: DailyQuotesViewModelFactory = DailyQuotesViewModelFactory()
    private val menuViewModelFactory: MenuViewModelFactory = MenuViewModelFactory()
    private val anchorViewModelFactory: AnchorViewModelFactory = AnchorViewModelFactory()
    private val futuresViewModelFactory: FuturesViewModelFactory = FuturesViewModelFactory()
    private val industryViewModelFactory: IndustryViewModelFactory = IndustryViewModelFactory()
    private val performanceViewModelFactory: PerformanceViewModelFactory = PerformanceViewModelFactory()
    private val searchViewModelFactory: SearchViewModelFactory = SearchViewModelFactory()

    fun <T : ViewModel> getViewModel(store: ViewModelStoreOwner, clazz: Class<T>): T {
        return ViewModelProvider(store, dailyQuotesViewModelFactory).get(clazz)
    }

    internal fun getDailyQuotesViewModelFactory(): DailyQuotesViewModelFactory {
        return dailyQuotesViewModelFactory
    }

    internal fun getMenuViewModelFactory(): MenuViewModelFactory {
        return menuViewModelFactory
    }

    internal fun getAnchorViewModelFactory(): AnchorViewModelFactory {
        return anchorViewModelFactory
    }

    internal fun getFuturesViewModelFactory(): FuturesViewModelFactory {
        return futuresViewModelFactory
    }

    internal fun getIndustryViewModelFactory(): IndustryViewModelFactory {
        return industryViewModelFactory
    }

    internal fun getPerformanceViewModelFactory(): PerformanceViewModelFactory {
        return performanceViewModelFactory
    }

    internal fun getSearchViewModelFactory(): SearchViewModelFactory {
        return searchViewModelFactory
    }

    /**
     * 受保护的权限,除了ViewModel，其它模块不应该需要Model层的实例
     */
    fun getDailyQuotesRepository(): DailyQuotesRepository {
        return DailyQuotesRepository(getNetHelper())
    }

    fun getNetHelper(): NetHelper {
        return NetHelper
    }

    fun getMenuRepository(): MenuRepository {
        return MenuRepository(getNetHelper())
    }

    fun getAnchorRepository(): AnchorRepository {
        return AnchorRepository(getNetHelper())
    }

    fun getFuturesRepository(): FuturesRepository {
        return FuturesRepository(getNetHelper())
    }

    fun getIndustryRepository(): IndustryRepository {
        return IndustryRepository(getNetHelper())
    }

    fun getPerformanceRepository(): PerformanceRepository {
        return PerformanceRepository(getNetHelper())
    }

    fun getSearchRepository(): SearchRepository {
        return SearchRepository(getNetHelper())
    }
}