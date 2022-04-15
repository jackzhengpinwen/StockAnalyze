package com.zpw.stockanalyze

import android.app.Application
import com.zpw.stockanalyze.internal.utils.AppGlobal
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import timber.log.Timber
import timber.log.Timber.*


class App: Application() {
    companion object {
        val openHttpLog = true
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        // 读取接口参数
        AssetsUtils.getAPIArgs("api_args.txt", this)
        // 读取token
        AssetsUtils.getToken("token.txt", this)
        // 读取股票代码
        AssetsUtils.getData(this)
    }
}