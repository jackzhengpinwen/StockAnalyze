package com.zpw.stockanalyze.mvvm.industry

import com.zpw.stockanalyze.internal.network.*
import com.zpw.stockanalyze.internal.utils.AssetsUtils
import com.zpw.stockanalyze.mvvm.model.BaseRepository
import com.zpw.stockanalyze.mvvm.utils.DateUtils

class IndustryRepository: BaseRepository() {

    suspend fun getValueData(): List<Industry> {
        return netHelper.getIndustry().getIndustry(
            "all",
            "code,name,cfg",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }

    suspend fun getDetailDataindustry(industry: String): List<IndustryDetail> {
        return netHelper.getIndustry().getIndustryDetail(
            industry,
            "2022-01-01",
            DateUtils.getCurrentDay(),
            "code,tdate,name,price,zdfd,cje",
            "1",
            AssetsUtils.TOKEN
        ).body()?.data ?: emptyList()
    }
}