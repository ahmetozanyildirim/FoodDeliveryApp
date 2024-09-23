package com.example.aciktim.retrofit

class ApiUtilsSepetYemekler {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getSepetYemeklerDao() : SepetYemeklerDao {
            try {
                return RetrofitClient.getClient(BASE_URL).create(SepetYemeklerDao::class.java)
            }catch (e: Exception) {
                throw e
            }

        }
    }
}