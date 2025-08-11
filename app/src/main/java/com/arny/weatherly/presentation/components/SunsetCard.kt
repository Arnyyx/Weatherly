package com.arny.weatherly.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.domain.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunsetCard(
    weatherData: WeatherResponse?,
    modifier: Modifier = Modifier,
) {
    // Lấy thời gian sunrise và sunset từ API (đơn vị: giây Unix timestamp)
    val sunriseTimestamp = weatherData?.current?.sunrise
    val sunsetTimestamp = weatherData?.current?.sunset

    // Chuyển đổi timestamp thành thời gian hiển thị (HH:mm)
    val sunriseTime = sunriseTimestamp?.let { timestamp ->
        val date = Date(timestamp * 1000L) // Convert to milliseconds
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.format(date)
    } ?: "--:--"

    val sunsetTime = sunsetTimestamp?.let { timestamp ->
        val date = Date(timestamp * 1000L)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.format(date)
    } ?: "--:--"

    // Tính toán vị trí mặt trời hiện tại
    val currentTimeMillis = System.currentTimeMillis() / 1000L // Current time in seconds
    val sunProgress = if (sunriseTimestamp != null && sunsetTimestamp != null) {
        when {
            currentTimeMillis < sunriseTimestamp -> 0f // Before sunrise
            currentTimeMillis > sunsetTimestamp -> 1f // After sunset
            else -> {
                // Calculate progress from sunrise to sunset (0f to 1f)
                val totalDayTime = sunsetTimestamp - sunriseTimestamp
                val currentDayTime = currentTimeMillis - sunriseTimestamp
                (currentDayTime.toFloat() / totalDayTime.toFloat()).coerceIn(0f, 1f)
            }
        }
    } else {
        0.5f // Default to middle position if no data
    }

    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Sunset",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = sunsetTime,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val center = androidx.compose.ui.geometry.Offset(width / 2, height)
                    val radius = width * 0.4f

                    // Draw sun path arc
                    drawArc(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF4A90E2),
                                Color(0xFFFFD700),
                                Color(0xFFFF8C00),
                                Color(0xFF4A90E2)
                            )
                        ),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = androidx.compose.ui.geometry.Offset(
                            center.x - radius,
                            center.y - radius
                        ),
                        size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                        style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Calculate current sun position based on real time
                    val sunAngle =
                        180 + (180 * sunProgress) // 0% = sunrise (180°), 100% = sunset (360°)
                    val sunX =
                        center.x + (radius * cos(Math.toRadians(sunAngle.toDouble()))).toFloat()
                    val sunY =
                        center.y + (radius * sin(Math.toRadians(sunAngle.toDouble()))).toFloat()

                    // Draw sun with different colors based on time of day
                    val sunColor = when {
                        sunProgress < 0.1f || sunProgress > 0.9f -> Color(0xFFFF8C00) // Orange during sunrise/sunset
                        else -> Color(0xFFFFD700) // Golden yellow during day
                    }

                    drawCircle(
                        color = sunColor,
                        radius = 8.dp.toPx(),
                        center = androidx.compose.ui.geometry.Offset(sunX, sunY)
                    )
                }

                // Time labels - hiển thị thời gian thực từ API
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = sunriseTime,
                        fontSize = 12.sp
                    )
                    Text(
                        text = sunsetTime,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SunsetCardPreview() {
    SunsetCard(null)
}