package com.example.aciktim.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green700 = Color(0xFF4CAF50)
val Green900 = Color(0xFF2C6B2F)
val Green200 = Color(0xFFA5D6A7)
val Green300 = Color(0xFF81C784)



val CardColor = Color(0xFFFAFAFA)

val DarkCardColor = Color(0xFF383838)

sealed class ThemeColors(
    val CardColor :Color
){
    object Dark:ThemeColors(
        CardColor= DarkCardColor
    )
    object Light:ThemeColors(
        CardColor= CardColor
    )
}