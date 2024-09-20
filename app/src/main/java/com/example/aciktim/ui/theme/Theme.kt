package com.example.aciktim.ui.theme

import android.app.Activity
import android.content.res.Resources.Theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext




val AppBarColor = Color(0xFFDB0F0F)
// Light Theme Colors
private val LightPrimaryColor = Color(0xFFFF0000) // Kırmızı
private val LightBackgroundColor = Color(0xFFF0F0F0) // Beyaz
private val LightOnBackgroundColor = Color(0xFF000000) // Siyah
private val LightSurfaceColor = Color(0xFFFFFFFF) // Açık gri yüzey rengi
private val LightOnSurfaceColor = Color(0xFF000000) // Siyah, yüzeydeki yazılar için
private val LightCard = ThemeColors.Light.CardColor

// Dark Theme Colors
private val DarkPrimaryColor = Color(0xFFFF0000) // Kırmızı
private val DarkBackgroundColor = Color(0xFF121212) // Koyu siyah
private val DarkOnBackgroundColor = Color(0xFFFFFFFF) // Beyaz
private val DarkSurfaceColor = Color(0xFF1F1F1F) // Koyu gri yüzey rengi
private val DarkOnSurfaceColor = Color(0xFFFFFFFF) // Beyaz, yüzeydeki yazılar için

private val DarkCard = ThemeColors.Dark.CardColor

// Light Theme Color Palette
private val LightColors = lightColorScheme(
    primary = LightPrimaryColor,
    background = LightBackgroundColor,
    onBackground = LightOnBackgroundColor,
    surface = LightSurfaceColor,
    onSurface = LightOnSurfaceColor,
    onPrimaryContainer = LightCard
)

// Dark Theme Color Palette
private val DarkColors = darkColorScheme(
    primary = DarkPrimaryColor,
    background = DarkBackgroundColor,
    onBackground = DarkOnBackgroundColor,
    surface = DarkSurfaceColor,
    onSurface = DarkOnSurfaceColor,
    onPrimaryContainer = DarkCard
)

// Theme
@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Varsayılan yazı tipleri kullanılabilir
        content = content
    )
}
