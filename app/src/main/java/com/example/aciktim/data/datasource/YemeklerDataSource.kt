package com.example.aciktim.data.datasource

import com.example.aciktim.data.entity.Yemekler
import com.example.aciktim.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var ydao : YemeklerDao) {

    suspend fun yemekleriYukle() : List<Yemekler> = withContext(Dispatchers.IO){

        return@withContext ydao.yemekleriYukle().yemekler
    }
}