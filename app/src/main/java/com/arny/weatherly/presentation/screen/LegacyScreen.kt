package com.arny.weatherly.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // City name
            Text(
                text = "Hanoi",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Weather icon (placeholder)
            Text(
                text = "☀️",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Temperature
            Text(
                text = "25°C",
                fontSize = 48.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Weather description
            Text(
                text = "Sunny",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Additional info (humidity, wind)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Humidity", fontSize = 16.sp)
                    Text(text = "60%", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Wind", fontSize = 16.sp)
                    Text(text = "10 km/h", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}