package com.example.aciktim.uix.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aciktim.uix.viewmodel.CartViewModel
import com.example.aciktim.uix.viewmodel.DetailViewModel
import com.example.aciktim.uix.viewmodel.HomeViewModel

@Composable
fun PageTransitions(homeViewModel: HomeViewModel,detailViewModel: DetailViewModel,cartViewModel:CartViewModel)
{

    val navController = rememberNavController()

    
    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){
            Home(navController,homeViewModel)

        }
        composable(
            route ="detail/{yemek_id}",
            arguments = listOf(navArgument("yemek_id"){
                type = NavType.StringType
            })
        ){
            navBackStackEntry ->
            val yemek_id = navBackStackEntry.arguments?.getString("yemek_id")
            if (yemek_id != null) {
                Detail(navController,detailViewModel,yemek_id)
            }
        }

        composable("cart"){
            Cart(navController,cartViewModel)
        }

    }


}