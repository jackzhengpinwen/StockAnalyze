package com.zpw.stockanalyze.mvvm.model

import com.zpw.stockanalyze.App
import com.zpw.stockanalyze.internal.network.NetHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseRepositoryModule {

    @Singleton
    @Provides
    fun provideNetHelper(): NetHelper {
        return NetHelper()
    }
}