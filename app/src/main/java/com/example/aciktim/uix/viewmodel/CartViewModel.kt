package com.example.aciktim.uix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aciktim.data.entity.SepetYemekler

import com.example.aciktim.data.repository.SepetYemeklerRepository
import com.example.aciktim.data.repository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(var yrepo: YemeklerRepository,
                                        var syrepo: SepetYemeklerRepository)
    : ViewModel() {


    var sepetListesi = MutableLiveData<List<SepetYemekler>>()

    val kullanici_adi = "ahmetozan"



    fun sepettenTekTekSil(
        sepet_yemek_id: Int,
        kullanici_adi: String
    ) {
        viewModelScope.launch {
            try {

                val response = withContext(Dispatchers.IO) {
                    syrepo.sepettekiYemekleriGetir(kullanici_adi)
                }


                val anlikListe = response ?: emptyList()


                val yemek = anlikListe.find {
                    it.sepet_yemek_id == sepet_yemek_id
                }


                if (yemek != null) {
                    if (yemek.yemek_siparis_adet > 1) {


                        withContext(Dispatchers.IO) {
                            syrepo.sepettenSil(sepet_yemek_id, kullanici_adi)
                        }


                        val updatedYemek = yemek.copy(yemek_siparis_adet = yemek.yemek_siparis_adet - 1)
                        withContext(Dispatchers.IO) {
                            syrepo.sepeteEkle(
                                updatedYemek.yemek_adi,
                                updatedYemek.yemek_resim_adi,
                                updatedYemek.yemek_fiyat,
                                updatedYemek.yemek_siparis_adet,
                                kullanici_adi
                            )
                        }
                    } else {

                        withContext(Dispatchers.IO) {
                           sepettenSil(sepet_yemek_id, kullanici_adi)
                        }
                    }


                    val updatedResponse = withContext(Dispatchers.IO) {
                        syrepo.sepettekiYemekleriGetir(kullanici_adi)
                    }
                    sepetListesi.value = updatedResponse ?: emptyList()
                } else {
                    Log.e("CartError", "Food item not found in the cart.")
                }
            } catch (e: Exception) {

                Log.e("CartError", "Error processing the cart item: ${e.message}")
            }
        }
    }









    fun sepetYemekleriYukle(kullanici_adi:String){

        viewModelScope.launch {
            try {

                val response = withContext(Dispatchers.IO) {
                    syrepo.sepettekiYemekleriGetir(kullanici_adi)
                }


                sepetListesi.value = response ?: emptyList()
            } catch (e: Exception) {

                Log.e("CartError", "Error loading cart items: ${e.message}")
                sepetListesi.value = emptyList()
            }
        }
    }

    fun sepettenSil(sepet_yemek_id:Int,
                    kullanici_adi: String){
        viewModelScope.launch {
            try {

                withContext(Dispatchers.IO) {
                    syrepo.sepettenSil(sepet_yemek_id, kullanici_adi)
                }


                sepetYemekleriYukle(kullanici_adi)
            } catch (e: Exception) {

                Log.e("CartError", "Error deleting cart item: ${e.message}")
            }
        }
    }

    fun sepetiBosalt(kullanici_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val sepetYemekler = withContext(Dispatchers.IO) {
                    syrepo.sepettekiYemekleriGetir(kullanici_adi)
                }

                sepetYemekler?.forEach { yemek ->
                    withContext(Dispatchers.IO) {
                        syrepo.sepettenSil(yemek.sepet_yemek_id, kullanici_adi)
                    }
                }


                sepetYemekleriYukle(kullanici_adi)
            } catch (e: Exception) {
                Log.e("CartError", "Error clearing cart: ${e.message}")
            }
        }
    }
}