package com.arny.weatherly.presentation.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arny.weatherly.domain.model.Weather
import com.arny.weatherly.presentation.components.AQICard
import com.arny.weatherly.presentation.components.DailyForecastCard
import com.arny.weatherly.presentation.components.HourlyForecastCard
import com.arny.weatherly.presentation.components.HumidityCard
import com.arny.weatherly.presentation.components.PressureCard
import com.arny.weatherly.presentation.components.RealFeelCard
import com.arny.weatherly.presentation.components.SunsetCard
import com.arny.weatherly.presentation.components.UVCard
import com.arny.weatherly.presentation.components.WindCard
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.states.Message
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.theme.WeatherTheme
import com.arny.weatherly.presentation.viewmodels.LocationViewModel
import com.arny.weatherly.presentation.viewmodels.WeatherViewModel
import com.arny.weatherly.utils.WeatherIcon
import kotlin.math.roundToLong
import androidx.core.net.toUri

@Composable
fun HomeScreen(
    locationViewModel: LocationViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit,
    onAQIClick: () -> Unit,
    onDailyForecastClick: () -> Unit,
) {
    val context = LocalContext.current
    val locationState by locationViewModel.locationState.collectAsState()
    val weatherState by weatherViewModel.weatherState.collectAsState()

    LaunchedEffect(locationState) {
        when (val response = locationState.locationState) {
            is Response.Success -> {
                response.data.let { location ->
                    weatherViewModel.getWeather(location.latitude, location.longitude)
                }
            }

            else -> Unit
        }
    }
    WeatherTheme(WeatherTheme.Clear) {
        HomeScreenContent(
            locationState,
            weatherState,
            onRefreshClick = {
                locationViewModel.fetchLocation(context)
            },
            onAddClick,
            onMenuClick,
            onDailyForecastClick,
            onAQIClick,
        )
    }

}

@Composable
private fun HomeScreenContent(
    locationState: LocationState,
    weatherState: WeatherState,
    onRefreshClick: () -> Unit,
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDailyForecastClick: () -> Unit,
    onAQIClick: () -> Unit
) {
    val weatherData = when (val state = weatherState.weatherState) {
        is Response.Success -> state.data
        else -> null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        TopBar(onRefreshClick, onAddClick, onMenuClick)
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                LocationSection(locationState)
            }
            item {
                MainTemperatureSection(weatherData)
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                DailyForecastCard(weatherData, onDailyForecastClick)
            }
            item {
                HourlyForecastCard(weatherData)
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UVCard(
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    HumidityCard(
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
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
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    WindCard(
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
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
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    PressureCard(
                        weatherData = weatherData,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
            }
            item {
                AQICard(
                    aqiValue = 0,
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
    onRefreshClick: () -> Unit,
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onRefreshClick) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onAddClick) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun LocationSection(
    locationState: LocationState
) {
    Column {
        AnimatedContent(
            targetState = locationState.locationState,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) + slideInVertically(animationSpec = tween(300)) { it / 2 })
                    .togetherWith(
                        fadeOut(animationSpec = tween(300)) + slideOutVertically(
                            animationSpec = tween(
                                300
                            )
                        ) { -it / 2 })
            },
            label = "LocationStateTransition"
        ) { state ->
            when (state) {
                is Response.Loading -> {
                    Text(
                        text = "Fetching location...",
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp,
                    )
                }

                is Response.Success -> {
                    state.data.let { location ->
                        Column {
                            state.warning?.message?.takeIf { it.isNotBlank() }?.let {
                                Text(
                                    text = it,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                )
                            }
                            Text(
                                text = "${location.ward}, ${location.city}",
                                fontWeight = FontWeight.Light,
                                fontSize = 24.sp,
                            )
                        }

                    }
                }

                is Response.Error -> {
                    HandleMessage(state.error)
                }

                is Response.Idle -> {
                    Text(
                        text = "-",
                        fontSize = 24.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun HandleMessage(
    message: Message,
    onNoInternetClick: () -> Unit = {}
) {
    val context = LocalContext.current
    when (message) {
        is Message.NoInternet -> {
            TextButton(onClick = { onNoInternetClick }) {
                Text(message.message)
            }
        }

        is Message.LocationPermissionDenied -> {
            TextButton(onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = "package:${context.packageName}".toUri()
                    }
                )
            }) {
                Text("Mở cài đặt")
            }
        }

        is Message.LocationDisabled -> {
            TextButton(onClick = {
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }) {
                Text("Bật vị trí")
            }
        }

        else -> Unit
    }
}

@Composable
fun MainTemperatureSection(
    weatherData: Weather?
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = weatherData?.current?.temperature?.roundToLong()?.toString()
                        ?.let { "$it°" }
                        ?: "-",
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Thin,
                    lineHeight = 120.sp
                )
                WeatherIcon(
                    iconCode = weatherData?.current?.conditions?.firstOrNull()?.icon.orEmpty(),
                    iconSize = 70.dp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = weatherData?.current?.conditions?.firstOrNull()?.description
                        ?: "-",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                )
                WeatherIcon(
                    iconCode = weatherData?.current?.conditions?.firstOrNull()?.icon.orEmpty(),
                    iconSize = 24.dp
                )
            }
            Text(
                text = weatherData?.current?.feelsLike?.roundToLong()
                    ?.let { "Feels like $it°" }
                    ?: "-",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
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
            text = "Data provided in part by OpenWeather",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        onAddClick = {},
        onMenuClick = {},
        onAQIClick = {},
        onDailyForecastClick = {}
    )
}