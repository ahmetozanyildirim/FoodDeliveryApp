package com.example.aciktim.retrofit

class ApiUtilsYemekler {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYemeklerDao() : YemeklerDao {
            try {
                return RetrofitClient.getClient(BASE_URL).create(YemeklerDao::class.java)
            }catch (e: Exception) {
                throw e
        }
    }




}}