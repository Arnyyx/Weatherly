package com.arny.weatherly.presentation.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val SunnyColorScheme = lightColorScheme(
    primary = Color(0xFFFFC107),
    background = Color(0xFFFFF8E1),
    surface = Color(0xFFFFECB3),
    onPrimary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

val RainyColorScheme = lightColorScheme(
//    surfaceContainerHighest = Color.Black, //Card background
    primary = Color(0xFF2196F3),
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

val SnowyColorScheme = lightColorScheme(
    primary = Color(0xFF90CAF9),
    background = Color(0xFFE1F5FE),
    surface = Color(0xFFB3E5FC),
    onPrimary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

