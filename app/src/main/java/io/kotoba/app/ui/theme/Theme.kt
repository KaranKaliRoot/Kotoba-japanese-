package io.kotoba.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
val KotobaPaper = Color(0xFFFAF7F2)
val KotobaInk = Color(0xFF2C221E)
val KotobaInkSoft = Color(0xFF6B5E56)
val KotobaMoss = Color(0xFFE3EBE4)
val KotobaMossDeep = Color(0xFFC8D4CC)
val KotobaClay = Color(0xFFF4E6DF)
val KotobaSand = Color(0xFFEFEAD8)
val KotobaButter = Color(0xFFF2ECE1)
val KotobaAccent = Color(0xFFC0392B)
val KotobaAccentSoft = Color(0xFFE8A090)
val KotobaTerracotta = Color(0xFF9E5A3C)
val KotobaWood = Color(0xFFD4C3A8)

val LightColorScheme = lightColorScheme(
    primary = KotobaInk,
    onPrimary = KotobaPaper,
    secondary = KotobaInkSoft,
    onSecondary = KotobaPaper,
    tertiary = KotobaAccent,
    background = KotobaPaper,
    onBackground = KotobaInk,
    surface = KotobaPaper,
    onSurface = KotobaInk,
    surfaceVariant = KotobaClay,
    onSurfaceVariant = KotobaInkSoft,
    outline = KotobaInkSoft,
    error = KotobaAccent
)

val DarkColorScheme = darkColorScheme(
    primary = KotobaSand,
    onPrimary = KotobaInk,
    secondary = KotobaMoss,
    onSecondary = KotobaInk,
    background = Color(0xFF14110E),
    onBackground = KotobaSand,
    surface = Color(0xFF1A1714),
    onSurface = KotobaSand,
    surfaceVariant = Color(0xFF26211C),
    onSurfaceVariant = KotobaMoss,
    outline = KotobaMossDeep,
    error = KotobaAccentSoft
)

@Composable
fun KotobaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = io.kotoba.app.ui.theme.Typography,
        content = content
    )
}