package com.zpw.stockanalyze.mvvm.model

import com.zpw.stockanalyze.internal.network.NetHelper
import com.zpw.stockanalyze.internal.utils.AppGlobal
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

abstract class BaseRepository {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BaseRepositoryInterface {
        fun getNetHelper(): NetHelper
    }

    val netHelper: NetHelper = EntryPointAccessors.fromApplication(
        AppGlobal.getApplication(),
        BaseRepositoryInterface::class.java
    ).getNetHelper()
}