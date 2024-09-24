


package com.example.aciktim.uix.view


import android.media.MediaPlayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aciktim.R
import com.example.aciktim.data.entity.Yemekler
import com.example.aciktim.ui.theme.AppBarColor
import com.example.aciktim.ui.theme.CherryFamily
import com.example.aciktim.uix.viewmodel.DetailViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(
    navController: NavController,
    detailViewModel: DetailViewModel,
    yemek_id:String
) {



    var yemekLitesi = detailViewModel.yemekListesi.observeAsState(listOf())
    var yemek_index = yemek_id.toInt() -1

    LaunchedEffect(key1 = yemek_id) {
        detailViewModel.yemekleriYukle()
    }






    if (yemekLitesi.value[yemek_index] == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        DetailContent(
            yemek = yemekLitesi.value[yemek_index],
            navController = navController,
            detailViewModel
        )
    }



}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    yemek: Yemekler,
    navController: NavController,
    detailViewModel: DetailViewModel
) {
    var quantity by remember { mutableStateOf(1) }
    val kullanici_adi = "ahmetozan"
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.buttonsound) }

    val addedToCartMessage = stringResource(id = R.string.added_to_cart)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name), fontFamily = CherryFamily) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBarColor,
                    titleContentColor = Color.White
                )
            )

        }
    ) { paddingValues ->
        val animatedQuantity by animateFloatAsState(targetValue = quantity.toFloat())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"

            // Large food image
            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .size(200.dp)
                    ,
                contentScale = ContentScale.Crop
            )

            // Large food name
            Text(
                text = yemek.yemek_adi,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            // Large food price
            Text(
                text = "${stringResource(id = R.string.price)}: \$${yemek.yemek_fiyat}",
                fontSize = 24.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            // Quantity selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { if (quantity > 1) quantity -= 1 },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                ) {
                    Text(text = "-", fontSize = 24.sp,color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = animatedQuantity.toInt().toString()  ,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(60.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))


                Button(
                    onClick = {
                        quantity += 1 },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                ) {
                    Text(text = "+", fontSize = 24.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Order Button
            Button(
                onClick = {
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    }
                    detailViewModel.sepeteEkle(yemek.yemek_adi,yemek.yemek_resim_adi,yemek.yemek_fiyat,quantity,kullanici_adi)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("${quantity} ${yemek.yemek_adi} $addedToCartMessage")
                    }

                },
                modifier = Modifier.fillMaxWidth().padding(20.dp)

                    .shadow(8.dp, RoundedCornerShape(16.dp))
            ) {
                Text(text = stringResource(id = R.string.order_now), fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
