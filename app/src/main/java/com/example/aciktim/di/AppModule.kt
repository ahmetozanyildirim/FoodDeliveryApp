package com.example.aciktim.di

import com.example.aciktim.data.datasource.SepetYemeklerDataSource
import com.example.aciktim.data.datasource.YemeklerDataSource
import com.example.aciktim.data.repository.SepetYemeklerRepository
import com.example.aciktim.data.repository.YemeklerRepository
import com.example.aciktim.retrofit.ApiUtilsSepetYemekler
import com.example.aciktim.retrofit.ApiUtilsYemekler
import com.example.aciktim.retrofit.SepetYemeklerDao
import com.example.aciktim.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideYemeklerRepository(yds:YemeklerDataSource) : YemeklerRepository {
        return YemeklerRepository(yds)
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao: YemeklerDao) : YemeklerDataSource {
        return YemeklerDataSource(ydao)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao() : YemeklerDao {
        return ApiUtilsYemekler.getYemeklerDao()
    }




    @Provides
    @Singleton
    fun provideSepetYemeklerRepository(syds:SepetYemeklerDataSource) : SepetYemeklerRepository {
        return SepetYemeklerRepository(syds)
    }

    @Provides
    @Singleton
    fun provideSepetYemeklerDataSource(sydao: SepetYemeklerDao) : SepetYemeklerDataSource {
        return SepetYemeklerDataSource(sydao)
    }

    @Provides
    @Singleton
    fun provideSepetYemeklerDao() : SepetYemeklerDao {
        return ApiUtilsSepetYemekler.getSepetYemeklerDao()
    }
}
