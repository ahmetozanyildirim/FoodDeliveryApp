package com.example.aciktim.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
    fun getClient(baseUrl:String) : Retrofit {

        try {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            throw e
        }

    }
    }
}