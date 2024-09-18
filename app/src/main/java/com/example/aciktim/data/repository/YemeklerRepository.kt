package com.example.aciktim.data.repository

import com.example.aciktim.data.datasource.YemeklerDataSource
import com.example.aciktim.data.entity.Yemekler

class YemeklerRepository(var yds:YemeklerDataSource) {

    suspend fun yemekleriYukle() : List<Yemekler> = yds.yemekleriYukle()
}