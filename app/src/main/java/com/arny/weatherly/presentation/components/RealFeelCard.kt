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
                            colors = listOf(Color.Cyan, Color.Green, Color.Red)
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

@Preview
@Composable
fun RealFeelCardPreview() {
    RealFeelCard(cardColor = Color.White)
}