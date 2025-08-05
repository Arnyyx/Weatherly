package com.arny.weatherly.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arny.weatherly.presentation.screens.HomeScreen
import com.arny.weatherly.presentation.screens.LegacyScreen
import com.arny.weatherly.presentation.screens.LocationScreen
import com.arny.weatherly.presentation.screens.TestScreen
import com.arny.weatherly.presentation.viewmodels.LocationViewModel


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "TestScreen") {
        composable("home") {
            HomeScreen(
                { toastPlaceHolder(navController.context, "Add clicked") },
                { toastPlaceHolder(navController.context, "Menu clicked") },
                { toastPlaceHolder(navController.context, "onAQIClick clicked") }
            )
        }

        composable("LocationScreen") {
            LocationScreen()
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