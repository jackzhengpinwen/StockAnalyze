package com.zpw.stockanalyze.internal.network

import com.zpw.stockanalyze.App
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
class NetModule {
    companion object {
        val BASE_URL = "http://api.waizaowang.com"
    }

    @Singleton
    @Provides
    fun provideService(): Retrofit {
        val logging = HttpLoggingInterceptor {
            if (App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}