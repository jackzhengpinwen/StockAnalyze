package com.zpw.stockanalyze.internal.network

import com.zpw.stockanalyze.App
import com.zpw.stockanalyze.internal.utils.AppGlobal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object NetHelper {
    fun getDailyQuotes(): DailyQuotes {
        val logging = HttpLoggingInterceptor {
            if(App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val dailyQuotes = Retrofit.Builder()
            .baseUrl("http://api.waizaowang.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DailyQuotes::class.java)

        return dailyQuotes
    }

    fun getFutures(): Futures {
        val logging = HttpLoggingInterceptor {
            if(App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val futures = Retrofit.Builder()
            .baseUrl("http://api.waizaowang.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Futures::class.java)

        return futures
    }

    fun getIndustry(): Industrys {
        val logging = HttpLoggingInterceptor {
            if(App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val industry = Retrofit.Builder()
            .baseUrl("http://api.waizaowang.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Industrys::class.java)

        return industry
    }

    fun getCW(): CWs {
        val logging = HttpLoggingInterceptor {
            if(App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val cw = Retrofit.Builder()
            .baseUrl("http://api.waizaowang.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CWs::class.java)

        return cw
    }

    fun getStock(): Stocks {
        val logging = HttpLoggingInterceptor {
            if(App.openHttpLog) {
                Timber.tag("OkHttp").d(it)
            }
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val stock = Retrofit.Builder()
            .baseUrl("http://api.waizaowang.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Stocks::class.java)

        return stock
    }
}