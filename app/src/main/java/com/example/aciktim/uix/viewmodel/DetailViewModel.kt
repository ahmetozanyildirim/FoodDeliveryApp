package com.example.aciktim.uix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aciktim.data.entity.Yemekler
import com.example.aciktim.data.repository.SepetYemeklerRepository
import com.example.aciktim.data.repository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    var yrepo: YemeklerRepository,
    var syrepo: SepetYemeklerRepository
) : ViewModel() {

    var yemekListesi= MutableLiveData<List<Yemekler>>()



    init {
        yemekleriYukle()
    }


    fun yemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            yemekListesi.value = yrepo.yemekleriYukle()
        }
    }

    fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            syrepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }


}






