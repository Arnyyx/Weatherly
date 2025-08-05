package com.arny.weatherly.presentation.screens

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.arny.weatherly.presentation.components.*
import com.arny.weatherly.utils.LocationUtils
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.lazy.LazyColumn
import androidx.hilt.navigation.compose.hiltViewModel
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.viewmodels.LocationViewModel

@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit,
    onAQIClick: () -> Unit,
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val locationState by locationViewModel.locationState.collectAsState()
    var isLocationEnabled by remember { mutableStateOf(locationUtils.isLocationEnabled()) }
    var permissionDenied by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (locationUtils.isLocationEnabled()) {
                locationViewModel.fetchLocation()
            } else {
                isLocationEnabled = false
            }
        } else {
            permissionDenied = true
        }
    }

    val locationSettingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (locationUtils.isLocationEnabled()) {
            isLocationEnabled = true
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationViewModel.fetchLocation()
            }
        } else {
            isLocationEnabled = false
        }
    }

    val requestLocationUpdate: () -> Unit = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (locationUtils.isLocationEnabled()) {
                locationViewModel.fetchLocation()
            } else {
                isLocationEnabled = false
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                locationSettingsLauncher.launch(intent)
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (locationUtils.isLocationEnabled()) {
                locationViewModel.fetchLocation()
            } else {
                isLocationEnabled = false
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val gradientColors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7BB3F0),
        Color(0xFF9FC5F8),
        Color(0xFFB8D4FF)
    )
    val cardAlpha = Color.Transparent.copy(alpha = 0.1f)

//    val mockWeather = Weather(
//        aqi = 71,
//    )

    HomeScreenContent(
        gradientColors,
        onAddClick,
        onMenuClick,
        isLocationEnabled,
        locationState,
        requestLocationUpdate,
        locationSettingsLauncher,
        permissionDenied,
        cardAlpha,
//        mockWeather,
        onAQIClick = onAQIClick
    )
}

@Composable
private fun HomeScreenContent(
    gradientColors: List<Color>,
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit,
    isLocationEnabled: Boolean,
    locationState: LocationState,
    requestLocationUpdate: () -> Unit,
    locationSettingsLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    permissionDenied: Boolean,
    cardAlpha: Color,
//    mockWeather: Weather,
    onAQIClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(horizontal = 24.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        TopBar(onAddClick, onMenuClick)
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                LocationSection(
                    isLocationEnabled = isLocationEnabled,
                    locationState = locationState,
                    onRequestLocationUpdate = requestLocationUpdate,
                    onOpenLocationSettings = {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        locationSettingsLauncher.launch(intent)
                    },
                    permissionDenied = permissionDenied
                )
            }
            item {
                MainTemperatureSection()
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                DailyForecastCard(cardAlpha)
            }
            item {
                HourlyForecastCard(cardAlpha)
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UVCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                    HumidityCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RealFeelCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                    WindCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SunsetCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                    PressureCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        cardColor = cardAlpha
                    )
                }
            }
            item {
                AQICard(
                    aqiValue = 0,
                    cardAlpha,
                    onAQIClick = onAQIClick
                )
            }
            item {
                FooterCard()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun TopBar(
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onAddClick) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun LocationSection(
    isLocationEnabled: Boolean,
    locationState: LocationState,
    onRequestLocationUpdate: () -> Unit,
    onOpenLocationSettings: () -> Unit,
    permissionDenied: Boolean
) {
    Column {
        when {
            permissionDenied -> {
                Text(
                    text = "Quyền truy cập vị trí bị từ chối",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
            }

            !isLocationEnabled -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Turn on location services",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                    IconButton(onClick = onOpenLocationSettings) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Arrow",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            else -> {
                when (locationState) {
                    is LocationState.Loading -> {
                        Text(
                            text = "Đang cập nhật",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    is LocationState.Success -> {
                        Text(
                            text = locationState.location.ward.takeIf { it.isNotEmpty() }
                                ?: "Không xác định",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    is LocationState.Error -> {
                        Text(
                            text = "Không xác định",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
        if (isLocationEnabled && !permissionDenied) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRequestLocationUpdate,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Lấy lại vị trí")
            }
        }
    }
}

@Composable
fun MainTemperatureSection() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = "Sun",
            tint = Color.Yellow,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "28°",
            color = Color.White,
            fontSize = 120.sp,
            fontWeight = FontWeight.Thin,
            lineHeight = 120.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Clear  36° / 26°",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Air,
                contentDescription = "AQI",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "AQI 71",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FooterCard() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Data provided in part by ⚙ AccuWeather",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}