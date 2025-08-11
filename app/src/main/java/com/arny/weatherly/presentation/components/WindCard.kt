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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WindCard(
    weatherData: WeatherResponse?,
    modifier: Modifier = Modifier,
) {
    val windSpeed = weatherData?.current?.wind_speed ?: 0.0
    val windDirection = weatherData?.current?.wind_deg ?: 0

    // Convert wind degree to direction text
    val windDirectionText = getWindDirection(windDirection)

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp)),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = windDirectionText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Wind speed with unit
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%.1f", windSpeed),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "km/h",
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Wind compass
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = this.center
                    val radius = size.minDimension / 2 - 15.dp.toPx()

                    // Draw outer compass ring with gradient
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF64B5F6).copy(alpha = 0.3f),
                                Color(0xFF64B5F6).copy(alpha = 0.1f)
                            )
                        ),
                        radius = radius + 8.dp.toPx(),
                        center = center
                    )

                    // Draw compass circle
                    drawCircle(
                        color = Color(0xFF64B5F6).copy(alpha = 0.4f),
                        radius = radius,
                        center = center,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Draw compass directions with better positioning
                    val directions = listOf("N", "E", "S", "W")
                    val directionColors = listOf(
                        Color(0xFFE53E3E), // North - Red
                        Color.Gray,
                        Color.Gray,
                        Color.Gray
                    )

                    directions.forEachIndexed { index, direction ->
                        val angle = index * 90.0
                        val x = center.x + (radius * cos(Math.toRadians(angle - 90))).toFloat()
                        val y = center.y + (radius * sin(Math.toRadians(angle - 90))).toFloat()

                        // Draw direction markers
                        drawCircle(
                            color = directionColors[index],
                            radius = 6.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(x, y)
                        )

                        // Draw direction letters (you might need to add text drawing logic)
                    }

                    // Draw wind direction arrow using actual wind degree
                    if (windDirection > 0) {
                        val windAngle = windDirection.toDouble()
                        val arrowLength = radius * 0.7f

                        // Arrow end point
                        val endX =
                            center.x + (arrowLength * cos(Math.toRadians(windAngle - 90))).toFloat()
                        val endY =
                            center.y + (arrowLength * sin(Math.toRadians(windAngle - 90))).toFloat()

                        // Draw arrow line with gradient
                        drawLine(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF64B5F6),
                                    Color(0xFF1976D2)
                                )
                            ),
                            start = center,
                            end = androidx.compose.ui.geometry.Offset(endX, endY),
                            strokeWidth = 4.dp.toPx(),
                            cap = StrokeCap.Round
                        )

                        // Draw arrowhead
                        val arrowHeadLength = 12.dp.toPx()
                        val arrowHeadAngle = 25.0

                        // Left arrow line
                        val leftAngle = windAngle - 90 - arrowHeadAngle
                        val leftX =
                            endX - (arrowHeadLength * cos(Math.toRadians(leftAngle))).toFloat()
                        val leftY =
                            endY - (arrowHeadLength * sin(Math.toRadians(leftAngle))).toFloat()

                        drawLine(
                            color = Color(0xFF1976D2),
                            start = androidx.compose.ui.geometry.Offset(endX, endY),
                            end = androidx.compose.ui.geometry.Offset(leftX, leftY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )

                        // Right arrow line
                        val rightAngle = windAngle - 90 + arrowHeadAngle
                        val rightX =
                            endX - (arrowHeadLength * cos(Math.toRadians(rightAngle))).toFloat()
                        val rightY =
                            endY - (arrowHeadLength * sin(Math.toRadians(rightAngle))).toFloat()

                        drawLine(
                            color = Color(0xFF1976D2),
                            start = androidx.compose.ui.geometry.Offset(endX, endY),
                            end = androidx.compose.ui.geometry.Offset(rightX, rightY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    // Draw center dot
                    drawCircle(
                        color = Color(0xFF1976D2),
                        radius = 4.dp.toPx(),
                        center = center
                    )
                }
            }

            // Wind strength indicator
            Spacer(modifier = Modifier.height(16.dp))

            val windStrength = getWindStrength(windSpeed)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (windStrength) {
                        "Calm" -> Icons.Default.Remove
                        "Light" -> Icons.AutoMirrored.Filled.TrendingFlat
                        "Moderate" -> Icons.AutoMirrored.Filled.TrendingUp
                        "Strong" -> Icons.Default.KeyboardDoubleArrowUp
                        else -> Icons.AutoMirrored.Filled.TrendingFlat
                    },
                    contentDescription = "Wind Strength",
                    tint = getWindStrengthColor(windStrength),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = windStrength,
                    color = getWindStrengthColor(windStrength),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Helper function to convert wind degree to direction
fun getWindDirection(degrees: Int): String {
    return when (degrees) {
        in 0..11, in 349..360 -> "North"
        in 12..33 -> "NNE"
        in 34..56 -> "Northeast"
        in 57..78 -> "ENE"
        in 79..101 -> "East"
        in 102..123 -> "ESE"
        in 124..146 -> "Southeast"
        in 147..168 -> "SSE"
        in 169..191 -> "South"
        in 192..213 -> "SSW"
        in 214..236 -> "Southwest"
        in 237..258 -> "WSW"
        in 259..281 -> "West"
        in 282..303 -> "WNW"
        in 304..326 -> "Northwest"
        in 327..348 -> "NNW"
        else -> "Unknown"
    }
}

// Helper function to determine wind strength
fun getWindStrength(windSpeed: Double): String {
    return when {
        windSpeed < 1 -> "Calm"
        windSpeed < 12 -> "Light"
        windSpeed < 25 -> "Moderate"
        windSpeed < 40 -> "Strong"
        else -> "Very Strong"
    }
}

// Helper function to get wind strength color
fun getWindStrengthColor(strength: String): Color {
    return when (strength) {
        "Calm" -> Color(0xFF4CAF50)
        "Light" -> Color(0xFF8BC34A)
        "Moderate" -> Color(0xFFFF9800)
        "Strong" -> Color(0xFFFF5722)
        "Very Strong" -> Color(0xFFF44336)
        else -> Color.Gray
    }
}

@Preview
@Composable
fun WindCardPreview() {
    WindCard(weatherData = null)
}