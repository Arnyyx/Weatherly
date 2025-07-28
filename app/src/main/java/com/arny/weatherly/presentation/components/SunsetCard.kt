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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun SunsetCard(modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Sunset",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "18:37",
                color = Color.Black,
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
                        style = Stroke(width = 6.dp.toPx())
                    )

                    // Current sun position (around 75% through the day)
                    val sunAngle = 180 + (180 * 0.75) // 75% through sunset
                    val sunX =
                        center.x + (radius * cos(Math.toRadians(sunAngle.toDouble()))).toFloat()
                    val sunY =
                        center.y + (radius * sin(Math.toRadians(sunAngle.toDouble()))).toFloat()

                    drawCircle(
                        color = Color(0xFFFFD700),
                        radius = 8.dp.toPx(),
                        center = androidx.compose.ui.geometry.Offset(sunX, sunY)
                    )
                }

                // Time labels
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "05:29",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "18:37",
                        color = Color.Gray,
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
    SunsetCard(cardColor = Color.White)
}

