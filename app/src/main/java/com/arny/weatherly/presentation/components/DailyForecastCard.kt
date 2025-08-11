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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arny.weatherly.domain.model.WeatherResponse
import com.arny.weatherly.utils.WeatherIcon
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DailyForecastCard(
    weatherData: WeatherResponse?,
    onDailyForecastClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        onClick = onDailyForecastClick
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
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "7-day forecast",
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
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            weatherData?.daily?.take(3)?.forEachIndexed { index, weather ->
                ForecastItem(
                    day = weather.dt.toDayString(),
                    icon = weather.weather.getOrNull(0)?.icon.orEmpty(),
                    lowTemp = weather.temp.min.toIntOrNullString(),
                    highTemp = weather.temp.max.toIntOrNullString(),
                    isToday = index == 0
                )
                if (index < 2) HorizontalDivider()
            } ?: ForecastItem(
                day = "-",
                icon = "-",
                lowTemp = "-",
                highTemp = "-",
                isToday = false
            )
        }
    }
}

@Composable
fun ForecastItem(
    day: String,
    icon: String,
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
            fontSize = 16.sp,
            fontWeight = if (isToday) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        WeatherIcon(icon)
        Text(
            text = lowTemp,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(4.dp)
                .background(Color(0xFFFFA500), RoundedCornerShape(2.dp))
        )
        Text(
            text = highTemp,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )
    }
}

fun Long.toDayString(): String {
    val date = Date(this * 1000)
    val today = Calendar.getInstance().apply { time = Date() }
    val forecastDay = Calendar.getInstance().apply { time = date }

    return when {
        today.get(Calendar.DAY_OF_YEAR) == forecastDay.get(Calendar.DAY_OF_YEAR) &&
                today.get(Calendar.YEAR) == forecastDay.get(Calendar.YEAR) -> "Today"

        today.apply { add(Calendar.DAY_OF_YEAR, 1) }.get(Calendar.DAY_OF_YEAR) ==
                forecastDay.get(Calendar.DAY_OF_YEAR) &&
                today.get(Calendar.YEAR) == forecastDay.get(Calendar.YEAR) -> "Tomorrow"

        else -> SimpleDateFormat("EEE", Locale.getDefault()).format(date)
    }
}

fun Double?.toIntOrNullString(): String = this?.toInt()?.toString() ?: "-"


@Preview
@Composable
fun ForecastItemPr() {
    DailyForecastCard(null) {}
}