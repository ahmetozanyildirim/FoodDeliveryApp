package com.example.aciktim.data.datasource

import com.example.aciktim.data.entity.SepetYemekler
import com.example.aciktim.retrofit.SepetYemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SepetYemeklerDataSource(var sydao: SepetYemeklerDao) {

    suspend fun sepeteEkle(yemek_adi:String,
                           yemek_resim_adi:String,
                           yemek_fiyat:Int,
                           yemek_siparis_adet:Int,
                           kullanici_adi:String) {
        sydao.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }



    suspend fun sepettekiYemekleriGetir(kullanici_adi:String) : List<SepetYemekler> = withContext(Dispatchers.IO)
    {
        return@withContext sydao.sepettekiYemekleriGetir(kullanici_adi).sepet_yemekler
    }


    suspend fun sepettenSil(sepet_yemek_id:Int,
                            kullanici_adi:String){
        sydao.sepettenSil(sepet_yemek_id,kullanici_adi)
    }


}