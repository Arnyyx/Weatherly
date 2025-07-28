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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        tint = if (icon == Icons.Default.WbSunny) Color.Red else Color.Gray,
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


@Preview
@Composable
fun HourlyForecastCardPreview() {
    HourlyForecastCard(cardColor = Color.Gray.copy(alpha = 0.1f))
}