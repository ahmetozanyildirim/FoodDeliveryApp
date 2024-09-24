package com.example.aciktim.uix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aciktim.data.entity.SepetYemekler
import com.example.aciktim.data.entity.Yemekler
import com.example.aciktim.data.repository.SepetYemeklerRepository
import com.example.aciktim.data.repository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(var yrepo: YemeklerRepository,var syrepo:SepetYemeklerRepository): ViewModel() {

    var yemeklerListesi= MutableLiveData<List<Yemekler>>()
    var sepetListesi = MutableLiveData<List<SepetYemekler>>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    init{
yemekleriYukle()
    }

    fun yemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.value = true
        yemeklerListesi.value = yrepo.yemekleriYukle()
            _isLoading.value = false
}
    }

    fun sepeteEkle(yemek_adi:String,
                   yemek_resim_adi:String,
                   yemek_fiyat:Int,
                   yemek_siparis_adet:Int,
                   kullanici_adi:String){

        CoroutineScope(Dispatchers.Main).launch {
            syrepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }
    fun sepettenSil(sepet_yemek_id:Int,
                    kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch{
            syrepo.sepettenSil(sepet_yemek_id,kullanici_adi)
        }
    }

    fun sepetYemekleriYukle(kullanici_adi:String){

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = withContext(Dispatchers.IO) {
                    syrepo.sepettekiYemekleriGetir(kullanici_adi)
                }


                if (response.isNullOrEmpty()) {
                    sepetListesi.value = emptyList()
                } else {
                    sepetListesi.value = response
                }
            } catch (e: Exception) {

                Log.e("CartError", "Error loading cart items: ${e.message}")
                sepetListesi.value = emptyList()
            }
        }









}}