package com.arny.weatherly.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.domain.model.Weather

@Composable
fun UVCard(
    weatherData: Weather?,
    modifier: Modifier = Modifier,
) {
    // Get UV index from weather data
    val uvIndex = weatherData?.current?.uvIndex?.toFloat()
    val uvValue = uvIndex ?: 0f

    // Determine UV level text and color based on UV index
    val (uvLevelText, uvLevelColor) = when {
        uvValue <= 2f -> "Low" to Color(0xFF4CAF50)
        uvValue <= 5f -> "Moderate" to Color(0xFFFFEB3B)
        uvValue <= 7f -> "High" to Color(0xFFFF9800)
        uvValue <= 10f -> "Very High" to Color(0xFFFF5722)
        else -> "Extreme" to Color(0xFF9C27B0)
    }

    // Calculate arc sweep angle (UV index typically ranges from 0 to 12+)
    val maxUV = 12f
    val normalizedUV = (uvValue / maxUV).coerceIn(0f, 1f)
    val arcSweepAngle = normalizedUV * 270f

    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "UV",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uvLevelText,
                color = uvLevelColor,
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

                    // Background arc
                    drawArc(
                        color = Color.Gray.copy(alpha = 0.3f),
                        startAngle = 135f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // UV level arc with gradient colors
                    if (arcSweepAngle > 0f) {
                        drawArc(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50), // Low (Green)
                                    Color(0xFFFFEB3B), // Moderate (Yellow)
                                    Color(0xFFFF9800), // High (Orange)
                                    Color(0xFFFF5722), // Very High (Red)
                                    Color(0xFF9C27B0),  // Extreme (Purple)
                                ),
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                            ),
                            startAngle = 135f,
                            sweepAngle = arcSweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                }

                // UV Index number in the center
                Text(
                    text = if (uvIndex != null) uvValue.toInt().toString() else "--",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Additional info text
            Text(
                text = if (uvIndex != null) {
                    when {
                        uvValue <= 2f -> "Minimal protection needed"
                        uvValue <= 5f -> "Some protection required"
                        uvValue <= 7f -> "Protection essential"
                        uvValue <= 10f -> "Extra protection required"
                        else -> "Avoid being outside"
                    }
                } else {
                    "No data available"
                },
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun UVCardPreview() {
    UVCard(
        weatherData = null,
    )
}