package io.kotoba.app.ui.theme

import androidx.compose.material3.Typography as Material3Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Shippori Mincho is not bundled; Serif falls back to Noto Serif CJK on Android.
val Shippori = FontFamily.Serif

val Typography = Material3Typography(
    displayLarge = Material3Typography().displayLarge.copy(
        fontFamily = Shippori,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    headlineLarge = Material3Typography().headlineLarge.copy(
        fontFamily = Shippori,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = Material3Typography().headlineMedium.copy(
        fontFamily = Shippori,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    titleLarge = Material3Typography().titleLarge.copy(
        fontFamily = Shippori,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = Material3Typography().bodyLarge.copy(
        fontFamily = Shippori,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = Material3Typography().bodyMedium.copy(
        fontFamily = Shippori,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = Material3Typography().labelLarge.copy(
        fontFamily = Shippori,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)