package com.arny.weatherly.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arny.weatherly.presentation.screen.HomeScreen
import com.arny.weatherly.presentation.screen.WeatherScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                { toastPlaceHolder(navController.context, "Add clicked") },
                { toastPlaceHolder(navController.context, "Menu clicked") }
            )
        }
        composable("WeatherScreen") {
            WeatherScreen()
        }
    }
}

fun toastPlaceHolder(context: Context, string: String) {
    Toast.makeText(
        context,
        string,
        Toast.LENGTH_SHORT
    ).show()
}