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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.domain.model.Weather
import com.arny.weatherly.utils.WeatherIcon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToLong

@Composable
fun HourlyForecastCard(
    weatherData: Weather?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "24-hour forecast",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))


            Column {
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

                        val temps =
                            weatherData?.hourly?.take(7)?.map { it.temperature } ?: List(7) { 0.0 }
                        val maxTemp = temps.maxOrNull() ?: 1.0
                        val minTemp = temps.minOrNull() ?: 0.0
                        val tempRange = if (maxTemp == minTemp) 1.0 else maxTemp - minTemp

                        temps.forEachIndexed { index, temp ->
                            val x = width * (index / 6f)
                            val y = height * (1 - (temp - minTemp) / tempRange).toFloat()
                            if (index == 0) {
                                path.moveTo(x, y)
                            } else {
                                path.lineTo(x, y)
                            }
                        }

                        drawPath(
                            path = path,
                            color = Color(0xFFFF9500),
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }

                    // Temperature labels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        val temps =
                            weatherData?.hourly?.take(7)?.map { "${it.temperature.roundToLong()}°" }
                                ?: List(7) { "-" }
                        temps.forEach { temp ->
                            Text(
                                text = temp,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Weather icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val icons =
                        weatherData?.hourly?.take(7)?.map { it.conditions.firstOrNull()?.icon }
                            ?: List(7) { null }
                    icons.forEach { iconCode ->
                        WeatherIcon(
                            iconCode = iconCode.orEmpty(),
                            iconSize = 32.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Wind speeds
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val winds =
                        weatherData?.hourly?.take(7)?.map { "${it.windSpeed.roundToLong()}km/h" }
                            ?: List(7) { "-" }
                    winds.forEach { wind ->
                        Text(
                            text = wind,
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
                    val times = weatherData?.hourly?.take(7)?.map {
                        SimpleDateFormat(
                            "HH:mm",
                            Locale.getDefault()
                        ).format(Date(it.timestamp * 1000))
                    } ?: List(7) { "-" }
                    times.forEachIndexed { index, time ->
                        Text(
                            text = if (index == 0 && time != "-") "Now" else time,
                            fontSize = 11.sp
                        )
                    }
                }

            }
        }
    }
}