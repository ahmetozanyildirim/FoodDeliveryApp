package com.example.aciktim.uix.view

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aciktim.R
import com.example.aciktim.data.entity.SepetYemekler
import com.example.aciktim.data.entity.Yemekler
import com.example.aciktim.ui.theme.AppBarColor
import com.example.aciktim.ui.theme.CardColor
import com.example.aciktim.ui.theme.CherryFamily
import com.example.aciktim.ui.theme.DarkCardColor
import com.example.aciktim.uix.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel) {

    var yemeklerLitesi = homeViewModel.yemeklerListesi.observeAsState(listOf())
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isLoading by homeViewModel.isLoading.observeAsState(false)




    LaunchedEffect(key1 = true) {
        homeViewModel.yemekleriYukle()

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { query ->
                                searchQuery = query
                            },
                            placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                            ,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(text = stringResource(id = R.string.app_name), fontFamily = CherryFamily)
                    }
                },
                navigationIcon = {
                    if (isSearchActive) {
                        IconButton(onClick = {
                            isSearchActive = false
                            searchQuery = "" // Reset the search query when closing search
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    if (!isSearchActive) {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =if (isSearchActive) Color.Gray else AppBarColor, // Change background color based on search state
                    titleContentColor = if (isSearchActive) MaterialTheme.colorScheme.onBackground else Color.White
                )

            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to the cart screen
                    navController.navigate("cart")
                },

                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Go to Cart")
            }

        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    )
    { paddingValues ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val filteredYemekler = if (searchQuery.isEmpty()) {
                listOf()

            } else {
                yemeklerLitesi.value.filter {
                    it.yemek_adi.contains(searchQuery, ignoreCase = true)

                }.take(3)
            }


            Column(modifier = Modifier.padding(paddingValues)) {

                if (isSearchActive && filteredYemekler.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        filteredYemekler.forEach { yemek ->
                            SearchSuggestionItem(yemek = yemek, onItemClick = {
                                navController.navigate("detail/${yemek.yemek_id}")
                            })
                        }
                    }
                }
                YemekList(
                    navController,
                    yemekler = yemeklerLitesi.value,
                    homeViewModel,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope
                )


            }

        }


    }
}

@Composable
fun YemekList(navController: NavController,yemekler:List<Yemekler>,homeViewModel: HomeViewModel, snackbarHostState : SnackbarHostState, coroutineScope : CoroutineScope ){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(yemekler.size){index ->
            YemekCard(navController = navController, yemek = yemekler[index], homeViewModel =homeViewModel, snackbarHostState = snackbarHostState, coroutineScope = coroutineScope)
        }
    }
}


@Composable
fun YemekCard(yemek: Yemekler, navController: NavController,homeViewModel: HomeViewModel, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) { // Add a callback for button click
    val kullanici_adi = "denemeOzann"

    var showSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.buttonsound) }


    val quantity = remember {
        mutableStateOf(1)
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(260.dp)
            .clickable {
                navController.navigate("detail/${yemek.yemek_id}")
            },  // Increased height to accommodate the button
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"

            // Image
            GlideImage(
                imageModel = url,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            // Text
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = yemek.yemek_adi,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.price_label,yemek.yemek_fiyat),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Order Button
            Button(
                onClick = {
                    homeViewModel.sepeteEkle(
                        yemek.yemek_adi,
                        yemek.yemek_resim_adi,
                        yemek.yemek_fiyat,
                        quantity.value,
                        kullanici_adi
                    )
                    coroutineScope.launch {
                        showSnackbar = true
                        delay(2000)
                        showSnackbar = false
                    }
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    }


                },  // Call the onOrderClick callback when button is pressed
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)// Button fills the width of the card
            ) {
                Text(text = stringResource(id = R.string.add_to_cart), fontSize = 16.sp, color = Color.White)
            }
        }
    }
    AnimatedVisibility(visible = showSnackbar) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp), // Köşeleri yuvarlayarak eliptik görünüm sağlar
            shadowElevation = 4.dp,// Gölge efekti için
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.added_to_cart_message,yemek.yemek_adi),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,

                )
            }
        }
    }
}

@Composable
fun SearchSuggestionItem(yemek: Yemekler, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Yemek Resmi
        GlideImage(
            imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Yemek Adı
        Text(
            text = yemek.yemek_adi,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), thickness = 0.3.dp)
}

