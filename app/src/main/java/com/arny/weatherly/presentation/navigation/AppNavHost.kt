package com.arny.weatherly.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arny.weatherly.presentation.screens.CheckDefaultColors
import com.arny.weatherly.presentation.screens.HomeScreen
import com.arny.weatherly.presentation.screens.LegacyScreen
import com.arny.weatherly.presentation.screens.TestScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {

            HomeScreen(
                onAddClick = { toastPlaceHolder(navController.context, "Add clicked") },
                onMenuClick = { toastPlaceHolder(navController.context, "Menu clicked") },
                onAQIClick = { toastPlaceHolder(navController.context, "onAQIClick clicked") },
                onDailyForecastClick = {
                    toastPlaceHolder(
                        navController.context,
                        "onDailyForecastClick clicked"
                    )
                }
            )
        }
        composable("UVIndexChart") {
            CheckDefaultColors()
        }
        composable("LegacyScreen") {
            LegacyScreen()
        }
        composable("TestScreen") {
            TestScreen()
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