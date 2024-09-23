package com.example.aciktim.data.repository

import com.example.aciktim.data.datasource.SepetYemeklerDataSource
import com.example.aciktim.data.entity.SepetYemekler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SepetYemeklerRepository(var syds:SepetYemeklerDataSource) {

    suspend fun sepettenSil(sepet_yemek_id:Int,
                            kullanici_adi:String) = syds.sepettenSil(sepet_yemek_id, kullanici_adi)

    suspend fun sepeteEkle(yemek_adi:String,
                           yemek_resim_adi:String,
                           yemek_fiyat:Int,
                           yemek_siparis_adet:Int,
                           kullanici_adi:String) = syds.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat,yemek_siparis_adet,kullanici_adi)


    suspend fun sepettekiYemekleriGetir(kullanici_adi: String) :List<SepetYemekler> = syds.sepettekiYemekleriGetir(kullanici_adi)


}