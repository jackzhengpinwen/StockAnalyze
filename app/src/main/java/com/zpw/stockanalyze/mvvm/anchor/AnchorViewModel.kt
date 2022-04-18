package com.zpw.stockanalyze.mvvm.anchor

import android.content.Intent
import com.zpw.stockanalyze.internal.network.DailyQuote
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.dailyquotes.DailyQuotesActivity
import com.zpw.stockanalyze.mvvm.viewmodel.BaseViewModel

class LineDataViewModel(
    private val repo: AnchorRepository
) : BaseViewModel<AnchorRepository>(repo) {
    private val TAG: String = LineDataViewModel::class.java.simpleName

    val valueData = mutableListOf<Float>()
    val growData = mutableListOf<Float>()
    val anchorData = mutableListOf<Float>()

    fun comeToValue(activity: AnchorActivity) {
        val intent = Intent(activity, DailyQuotesActivity::class.java)
        intent.putExtra(DailyQuotesActivity.DATA_TYPE, AssetsUtils.ANCHOR_VALUE)
        activity.startActivity(intent)
    }

    fun comeToGrow(activity: AnchorActivity) {
        val intent = Intent(activity, DailyQuotesActivity::class.java)
        intent.putExtra(DailyQuotesActivity.DATA_TYPE, AssetsUtils.ANCHOR_GROW)
        activity.startActivity(intent)
    }

    fun comeToAnchor(activity: AnchorActivity) {
        val intent = Intent(activity, DailyQuotesActivity::class.java)
        intent.putExtra(DailyQuotesActivity.DATA_TYPE, AssetsUtils.ANCHOR)
        activity.startActivity(intent)
    }

    suspend fun getValueData(): List<Float> {
        val linedatas = repo.getValueData(AssetsUtils.ANCHOR_VALUE)
//        Timber.d("linedatas is ${linedatas}")
        val dateSet: MutableSet<String> = HashSet()
        // 保存日期
        linedatas.forEach {
            dateSet.add(it.tdate)
        }
        dateSet.sorted()
//        Timber.d("dateSet is ${dateSet}")
        // 保存日期对应的数据
        val dateToDataMap: MutableMap<String, MutableList<DailyQuote>> = HashMap()

        dateSet.forEach { date ->
            linedatas.forEach { linedata ->
                if (linedata.tdate == date) {
                    if (dateToDataMap[date] == null) {
                        dateToDataMap[date] = mutableListOf()
                    }
                    dateToDataMap[date]?.add(linedata)
                }
            }
        }
//        Timber.d("dateToDataMap is ${dateToDataMap}")
        // 计算每天的权重
        dateToDataMap.forEach { t, u ->
            valueData.add(getWeighted(u))
        }

        return valueData
    }

    suspend fun getGrowData(): List<Float> {
        val linedatas = repo.getValueData(AssetsUtils.ANCHOR_GROW)
//        Timber.d("linedatas is ${linedatas}")
        val dateSet: MutableSet<String> = HashSet()
        // 保存日期
        linedatas.forEach {
            dateSet.add(it.tdate)
        }
        dateSet.sorted()
//        Timber.d("dateSet is ${dateSet}")
        // 保存日期对应的数据
        val dateToDataMap: MutableMap<String, MutableList<DailyQuote>> = HashMap()
        dateSet.forEach { date ->
            linedatas.forEach { linedata ->
                if (linedata.tdate == date) {
                    if (dateToDataMap[date] == null) {
                        dateToDataMap[date] = mutableListOf()
                    }
                    dateToDataMap[date]?.add(linedata)
                }
            }
        }
//        Timber.d("dateToDataMap is ${dateToDataMap}")
        // 计算每天的权重
        dateToDataMap.forEach { t, u ->
            growData.add(getWeighted(u))
        }

        return growData
    }

    suspend fun getData(): List<Float> {
        val linedatas = repo.getValueData(AssetsUtils.ANCHOR)
//        Timber.d("linedatas is ${linedatas}")
        val dateSet: MutableSet<String> = HashSet()
        // 保存日期
        linedatas.forEach {
            dateSet.add(it.tdate)
        }
        dateSet.sorted()
//        Timber.d("dateSet is ${dateSet}")
        // 保存日期对应的数据
        val dateToDataMap: MutableMap<String, MutableList<DailyQuote>> = HashMap()
        dateSet.forEach { date ->
            linedatas.forEach { linedata ->
                if (linedata.tdate == date) {
                    if (dateToDataMap[date] == null) {
                        dateToDataMap[date] = mutableListOf()
                    }
                    dateToDataMap[date]?.add(linedata)
                }
            }
        }
//        Timber.d("dateToDataMap is ${dateToDataMap}")
        // 计算每天的权重
        dateToDataMap.forEach { t, u ->
            anchorData.add(getWeighted(u))
        }

        return anchorData
    }

    private fun getWeighted(dailyQuotes: List<DailyQuote>): Float {
        var allWeigted = dailyQuotes.map { it.ltsz }.sum()
//        Timber.d("allWeigted is $allWeigted")
        var allResult = dailyQuotes.map {
//            Timber.d("${it.ltsz / allWeigted * it.zdfd.toFloat()}")
            it.ltsz / allWeigted * it.zdfd.toFloat()
        }.sum()
//        Timber.d("allResult is $allResult")
        return allResult.toFloat()
    }
}