package com.arny.weatherly.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arny.weatherly.presentation.screens.HomeScreen
import com.arny.weatherly.presentation.screens.LegacyScreen
import com.arny.weatherly.presentation.screens.SearchScreen
import com.arny.weatherly.presentation.screens.SettingsScreen
import com.arny.weatherly.presentation.screens.TestScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(
                onAddClick = {
                    navController.navigate("SearchScreen")
                },
                onMenuClick = { navController.navigate("SettingsScreen") },
                onAQIClick = { toastPlaceHolder(navController.context, "onAQIClick clicked") },
                onDailyForecastClick = {
                    toastPlaceHolder(
                        navController.context,
                        "onDailyForecastClick clicked"
                    )
                }
            )
        }
        composable("SettingsScreen") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("SearchScreen") {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onCityClick = {
                    toastPlaceHolder(navController.context, "onCityClick")
                },
                onCityLongClick = {
                    toastPlaceHolder(navController.context, "onCityLongClick")
                }
            )
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