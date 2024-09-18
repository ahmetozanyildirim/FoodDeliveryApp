package com.example.aciktim.uix.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.aciktim.MyWorker
import com.example.aciktim.R
import com.example.aciktim.data.entity.SepetYemekler
import com.example.aciktim.ui.theme.AppBarColor
import com.example.aciktim.uix.viewmodel.CartViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.util.concurrent.TimeUnit

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Cart(navController: NavController, cartViewModel:CartViewModel) {

    val sepetYemekler = cartViewModel.sepetListesi.observeAsState(listOf())
    val kullanici_adi = "denemeOzann"

    val context = LocalContext.current






    LaunchedEffect(sepetYemekler) {
        cartViewModel.sepetYemekleriYukle(kullanici_adi)


    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.cart)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions =
                {
                    // Clear Cart Button with conditional enabled/disabled state
                    val isCartEmpty = sepetYemekler.value.isEmpty()

                    Button(
                        onClick = {
                            if (!isCartEmpty) cartViewModel.sepetiBosalt(kullanici_adi)
                        },
                        enabled = !isCartEmpty,  // Button is disabled if the cart is empty
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCartEmpty) Color.Gray else MaterialTheme.colorScheme.error, // Gray if disabled, Red if enabled
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear, // Clear icon
                            contentDescription = stringResource(id = R.string.clear_cart),
                            tint = if (isCartEmpty) Color.Gray else Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.clear_cart), fontSize = 16.sp)
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBarColor,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            if (sepetYemekler.value.isNotEmpty()) {
                val totalAmount =
                    sepetYemekler.value.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }
                val vat = (totalAmount * 0.18).toInt()
                val finalAmount = totalAmount + vat
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${stringResource(id = R.string.total)}: \$${totalAmount}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface

                        )
                        Text(
                            text = "${stringResource(id = R.string.vat)}: \$${vat}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${stringResource(id = R.string.final_amount)}: \$${finalAmount}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = {
                            val istek = OneTimeWorkRequestBuilder<MyWorker>().setInitialDelay(15,TimeUnit.SECONDS).build()
                            WorkManager.getInstance(context).enqueue(istek)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.order_now), fontSize = 18.sp)
                    }

                }
            }}
    )  { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (sepetYemekler.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.cart_empty), fontSize = 24.sp,color = MaterialTheme.colorScheme.onBackground)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    val groupedYemekler = sepetYemekler.value.groupBy { it.yemek_adi }
                    items(groupedYemekler.entries.toList()) { entry ->
                        val yemekAdi = entry.key
                        val yemekList = entry.value
                        val toplamAdet = yemekList.sumOf { it.yemek_siparis_adet }
                        val yemek = yemekList.first()

                        CartComponent(yemek = yemek.copy(yemek_siparis_adet = toplamAdet)) {
                            cartViewModel.sepettenTekTekSil(
                                kullanici_adi = kullanici_adi,
                                sepet_yemek_id = yemek.sepet_yemek_id
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CartComponent(yemek: SepetYemekler, sepetYemeklerDeleteClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
            GlideImage(
                imageModel = url,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(text = yemek.yemek_adi, fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Adet: ${yemek.yemek_siparis_adet}", fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurface)
            }
            Text(text = "Fiyat: ${yemek.yemek_fiyat * yemek.yemek_siparis_adet}",color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = sepetYemeklerDeleteClick) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Delete Task", tint = Color.Gray)
            }
        }
    }
}

