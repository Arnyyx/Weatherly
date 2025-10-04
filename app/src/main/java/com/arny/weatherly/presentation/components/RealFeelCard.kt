package com.arny.weatherly.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.domain.model.Weather
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RealFeelCard(
    weatherData: Weather?,
    modifier: Modifier = Modifier,
) {
    // Get the feels like temperature from weather data
    val feelsLike = weatherData?.current?.feelsLike

    val feelsLikeText = if (feelsLike != null) {
        "${feelsLike.toInt()}°"
    } else {
        "--°"
    }

    // Calculate needle position based on temperature (assuming range -10°C to 50°C)
    val needleAngle = if (feelsLike != null) {
        val minTemp = -10f
        val maxTemp = 50f
        val normalizedTemp = ((feelsLike - minTemp) / (maxTemp - minTemp)).coerceIn(0.0F, 1.0F)
        180f + (normalizedTemp * 180f) // 180° to 360° range
    } else {
        270f // Default to middle position
    }

    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Real feel",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = feelsLikeText,
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
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Temperature arc - shows current temperature range
//                    val arcSweepAngle = if (feelsLike != null) {
//                        val minTemp = -10f
//                        val maxTemp = 50f
//                        val normalizedTemp =
//                            ((feelsLike - minTemp) / (maxTemp - minTemp)).coerceIn(0.0F, 1.0F)
//                        normalizedTemp * 180f
//                    } else {
//                        90f
//                    }

                    val arcSweepAngle = if (feelsLike != null) {
                        val minTemp = -10f
                        val maxTemp = 50f
                        val normalizedTemp =
                            ((feelsLike - minTemp) / (maxTemp - minTemp)).coerceIn(0.0F, 1.0F)
                        normalizedTemp * 180f
                    } else {
                        90f
                    }

                    drawArc(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Blue,
                                Color.Red,
                            ),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                        ),
                        startAngle = 180f,
                        sweepAngle = arcSweepAngle,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Needle
                    val needleLength = size.minDimension / 2 - strokeWidth
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val angleRad = Math.toRadians(needleAngle.toDouble())
                    val endX = centerX + (needleLength * cos(angleRad)).toFloat()
                    val endY = centerY + (needleLength * sin(angleRad)).toFloat()

                    drawLine(
                        color = Color.Black,
                        start = center,
                        end = Offset(endX, endY),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
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

@Preview
@Composable
fun RealFeelCardPreview() {
    RealFeelCard(
        weatherData = null, // Preview with null data
    )
}