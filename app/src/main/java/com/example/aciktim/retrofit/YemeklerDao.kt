package com.example.aciktim.retrofit

import com.example.aciktim.data.entity.YemeklerCevap
import retrofit2.http.GET

interface YemeklerDao {

    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle(): YemeklerCevap



}