package com.arny.weatherly.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.viewmodels.LocationViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.utils.LocationUtils

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val locationState by viewModel.locationState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchLocation()
        } else {
            viewModel.onPermissionDenied()
        }
    }

    val locationSettingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.fetchLocation()
    }

    LaunchedEffect(Unit) {
        when {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED -> {
                val locationUtils = LocationUtils(context)
                Log.d("LocationScreen", "isLocationEnabled: ${locationUtils.isLocationEnabled()}")
                if (locationUtils.isLocationEnabled()) {
                    viewModel.fetchLocation()
                } else {
                    viewModel.onLocationServicesDisabled()
                }
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    LocationContent(
        state = locationState,
        onRetry = { viewModel.fetchLocation() },
        onEnableLocation = {
            locationSettingsLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    )
}

@Composable
private fun LocationContent(
    state: LocationState,
    onRetry: () -> Unit,
    onEnableLocation: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is LocationState.Loading -> {
                CircularProgressIndicator()
            }

            is LocationState.Success -> {
                LocationInfo(location = state.location)
            }

            is LocationState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = onRetry,
                    onEnableLocation = if (state.message.contains(
                            "enable location",
                            ignoreCase = true
                        )
                    ) {
                        onEnableLocation
                    } else null
                )
            }
        }
    }
}

@Composable
private fun LocationInfo(location: Location) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Location Information",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("Ward: ${location.ward}")
        Text("City: ${location.city}")
        Text("Country: ${location.country}")
        Text("Full Address: ${location.fullAddress}")
        Text("Latitude: ${location.latitude}")
        Text("Longitude: ${location.longitude}")
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    onEnableLocation: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
        if (onEnableLocation != null) {
            Button(onClick = onEnableLocation) {
                Text("Enable Location Services")
            }
        }
    }
}