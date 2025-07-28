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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


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
