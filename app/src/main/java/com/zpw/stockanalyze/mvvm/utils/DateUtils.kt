package com.zpw.stockanalyze.mvvm.utils

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDay(): String {
        val date = Date()
        val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("YYYY-MM-dd")
        } else {
            SimpleDateFormat("yyyy-MM-dd")
        };//设置当前时间的格式，为年-月-日
        return dateFormat.format(date)
    }
}