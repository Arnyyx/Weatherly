package com.arny.weatherly.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.presentation.components.AQICard
import com.arny.weatherly.presentation.components.DailyForecastCard
import com.arny.weatherly.presentation.components.HourlyForecastCard
import com.arny.weatherly.presentation.components.HumidityCard
import com.arny.weatherly.presentation.components.PressureCard
import com.arny.weatherly.presentation.components.RealFeelCard
import com.arny.weatherly.presentation.components.SunsetCard
import com.arny.weatherly.presentation.components.UVCard
import com.arny.weatherly.presentation.components.WindCard

@Composable
fun HomeScreen(onAddClick: () -> Unit, onMenuClick: () -> Unit) {
    val gradientColors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7BB3F0),
        Color(0xFF9FC5F8),
        Color(0xFFB8D4FF)
    )
    val cardAlpha = Color.Transparent.copy(alpha = 0.1f)
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
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                LocationSection()
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
                AQICard(cardAlpha)
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
fun LocationSection() {
    Column {
        Text(
            text = "Thanh Tri",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Turn on location services",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun MainTemperatureSection() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Sun icon placeholder
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen({}, {})
    }
}