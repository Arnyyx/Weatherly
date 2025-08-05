package com.arny.weatherly.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyForecastCard(cardColor: Color) {
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
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "5-day forecast",
                        color = Color.White.copy(alpha = 0.8f),
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
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        tint = Color.White.copy(alpha = 0.5f),
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
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
fun ForecastItem(
    day: String,
    icon: ImageVector,
    lowTemp: String,
    highTemp: String,
    isToday: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = if (isToday) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = icon,
            contentDescription = day,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = lowTemp,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .background(
                    Color(0xFFFFA500),
                    RoundedCornerShape(2.dp)
                )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = highTemp,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )
    }
}

@Preview(backgroundColor = 0xFF00FF00, showBackground = false)
@Composable
fun DailyForecastCardPreview() {
    DailyForecastCard(cardColor = Color.White.copy(alpha = 0.5f))
}