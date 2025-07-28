package com.arny.weatherly.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.arny.weatherly.presentation.components.AQICard
import com.arny.weatherly.presentation.components.ForecastItem
import com.arny.weatherly.presentation.components.PressureCard
import com.arny.weatherly.presentation.components.SunsetCard
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WeatherScreen() {
    val gradientColors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7BB3F0),
        Color(0xFF9FC5F8),
        Color(0xFFB8D4FF)
    )

    val listState = rememberLazyListState()
    val scrollOffset = listState.firstVisibleItemScrollOffset

    // Calculate card alpha based on scroll position
    val cardAlpha by animateColorAsState(
        targetValue = if (scrollOffset > 100) Color.White.copy(alpha = 0.95f) else Color.White.copy(
            alpha = 0.2f
        ),
        animationSpec = tween(300),
        label = "cardAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//                TopBar()
//            }
//
//            item {
//                LocationHeader()
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(200.dp)) // Space for main temp display
//            }
//
//            item {
//                ForecastCard(cardAlpha)
//            }
//
//            item {
//                HourlyForecastCard(cardAlpha)
//            }
//
//            item {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    UVCard(
//                        modifier = Modifier.weight(1f),
//                        cardColor = cardAlpha
//                    )
//                    HumidityCard(
//                        modifier = Modifier.weight(1f),
//                        cardColor = cardAlpha
//                    )
//                }
//            }
//
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RealFeelCard(
                        modifier = Modifier.weight(1f),
                        cardColor = cardAlpha
                    )
                    WindCard(
                        modifier = Modifier.weight(1f),
                        cardColor = cardAlpha
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SunsetCard(
                        modifier = Modifier.weight(1f),
                        cardColor = cardAlpha
                    )
                    PressureCard(
                        modifier = Modifier.weight(1f),
                        cardColor = cardAlpha
                    )
                }
            }

            item {
                AQICard(cardAlpha)
            }

            item {
                FooterCard()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "9:33",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Shield",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "0.07\nKB/s",
                color = Color.White,
                fontSize = 11.sp,
                textAlign = TextAlign.End,
                lineHeight = 12.sp
            )

            // Signal bars
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height((index + 1) * 2.dp + 4.dp)
                            .background(Color.White, RoundedCornerShape(1.dp))
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height((index + 1) * 2.dp + 4.dp)
                            .background(Color.White, RoundedCornerShape(1.dp))
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "WiFi",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            // Battery
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(12.dp)
                    .background(Color.White, RoundedCornerShape(2.dp))
            ) {
                Text(
                    text = "56",
                    color = Color.Black,
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun LocationHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Thanh Tri",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Light
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ForecastCard(cardColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Calendar",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "5-day forecast",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "More details",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ForecastItem(
                day = "Today",
                icon = Icons.Default.WbCloudy,
                lowTemp = "26°",
                highTemp = "36°"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ForecastItem(
                day = "Tomorrow",
                icon = Icons.Default.WbSunny,
                lowTemp = "26°",
                highTemp = "38°"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ForecastItem(
                day = "Wed",
                icon = Icons.Default.WbSunny,
                lowTemp = "27°",
                highTemp = "38°"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "5-day forecast",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun HourlyForecastCard(cardColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "24-hour forecast",
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Temperature chart line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path()
                    val width = size.width
                    val height = size.height

                    // Draw temperature line
                    path.moveTo(0f, height * 0.8f)
                    path.lineTo(width * 0.15f, height * 0.6f)
                    path.lineTo(width * 0.3f, height * 0.4f)
                    path.lineTo(width * 0.45f, height * 0.2f)
                    path.lineTo(width * 0.6f, height * 0.15f)
                    path.lineTo(width * 0.75f, height * 0.15f)
                    path.lineTo(width, height * 0.1f)

                    drawPath(
                        path = path,
                        color = Color(0xFFFF9500),
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                // Temperature labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    val temps = listOf("29°", "32°", "33°", "34°", "35°", "35°", "36°")
                    temps.forEach { temp ->
                        Text(
                            text = temp,
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather icons and wind speed
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val icons = listOf(
                    Icons.Default.WbSunny,
                    Icons.Default.WbCloudy,
                    Icons.Default.WbCloudy,
                    Icons.Default.WbCloudy,
                    Icons.Default.WbCloudy,
                    Icons.Default.WbCloudy,
                    Icons.Default.WbCloudy
                )
                icons.forEach { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = "Weather",
                        tint = if (icon == Icons.Default.WbSunny) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Wind speeds
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val winds = listOf(
                    "8.8km/h",
                    "9.3km/h",
                    "9.3km/h",
                    "9.3km/h",
                    "9.3km/h",
                    "9.3km/h",
                    "7.4km/h"
                )
                winds.forEach { wind ->
                    Text(
                        text = wind,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Time labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val times = listOf("Now", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00")
                times.forEach { time ->
                    Text(
                        text = time,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UVCard(modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "UV",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Weak",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 8.dp.toPx()
                    val radius = size.minDimension / 2 - strokeWidth / 2

                    // Background arc
                    drawArc(
                        color = Color.Gray.copy(alpha = 0.2f),
                        startAngle = 135f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )

                    // UV level arc (low level = green to yellow)
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Green,
                                Color.Yellow,
                                Color.Yellow,
                                Color.Red,
                                Color.Magenta
                            )
                        ),
                        startAngle = 135f,
                        sweepAngle = 54f, // 20% of 270 degrees for level 2
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                }

                Text(
                    text = "2",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HumidityCard(modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Humidity",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "82%",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 8.dp.toPx()

                    // Background arc
                    drawArc(
                        color = Color.Gray.copy(alpha = 0.2f),
                        startAngle = 135f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )

                    // Humidity arc (82%)
                    drawArc(
                        color = Color.Blue,
                        startAngle = 135f,
                        sweepAngle = 270f * 0.82f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Water,
                    contentDescription = "Water drop",
                    tint = Color.Blue,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RealFeelCard(modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Real feel",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "29°",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 8.dp.toPx()

                    // Background arc
                    drawArc(
                        color = Color.Gray.copy(alpha = 0.2f),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )

                    // Temperature arc
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(Color.Cyan, Color.Green, Color.Yellow)
                        ),
                        startAngle = 180f,
                        sweepAngle = 90f, // Mid-range temperature
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )

                    // Needle
                    val angle = 225.0 // 45 degrees from start
                    val needleLength = size.minDimension / 2 - strokeWidth
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val endX = centerX + (needleLength * cos(Math.toRadians(angle))).toFloat()
                    val endY = centerY + (needleLength * sin(Math.toRadians(angle))).toFloat()

                    drawLine(
                        color = Color.Black,
                        start = center,
                        end = androidx.compose.ui.geometry.Offset(endX, endY),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawCircle(
                        color = Color.Black,
                        radius = 4.dp.toPx(),
                        center = center
                    )
                }
            }
        }
    }
}

@Composable
fun WindCard(modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Northwest",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "8.8",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = this.center
                    val radius = size.minDimension / 2 - 10.dp.toPx()

                    // Draw compass circle
                    drawCircle(
                        color = Color.Gray.copy(alpha = 0.2f),
                        radius = radius,
                        center = center,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Draw compass directions
                    val directions = listOf("N", "E", "S", "W")
                    directions.forEachIndexed { index, direction ->
                        val angle = index * 90.0
                        val x = center.x + (radius * cos(Math.toRadians(angle - 90))).toFloat()
                        val y = center.y + (radius * sin(Math.toRadians(angle - 90))).toFloat()

                        drawCircle(
                            color = if (direction == "N") Color.Blue else Color.Gray,
                            radius = 8.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(x, y)
                        )
                    }

                    // Draw wind direction arrow (Northwest)
                    val windAngle = 315.0 // Northwest
                    val arrowLength = radius * 0.6f
                    val endX =
                        center.x + (arrowLength * cos(Math.toRadians(windAngle - 90))).toFloat()
                    val endY =
                        center.y + (arrowLength * sin(Math.toRadians(windAngle - 90))).toFloat()

                    drawLine(
                        color = Color.Blue,
                        start = center,
                        end = androidx.compose.ui.geometry.Offset(endX, endY),
                        strokeWidth = 3.dp.toPx()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "km/h",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    MaterialTheme {
        WeatherScreen()
    }
}