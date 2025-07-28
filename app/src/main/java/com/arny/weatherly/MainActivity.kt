package com.arny.weatherly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arny.weatherly.presentation.navigation.AppNavHost
import com.arny.weatherly.presentation.theme.WeatherlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherlyTheme {
                AppNavHost()
            }
        }
    }
}
