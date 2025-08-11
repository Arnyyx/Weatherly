package com.arny.weatherly.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckDefaultColors() {
    Column {
        Text("Text color: ${MaterialTheme.colorScheme.onSurface}")
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text("Card background: ${MaterialTheme.colorScheme.surface}")
            Text("Card content: ${MaterialTheme.colorScheme.onSurface}")
        }
        Text("Primary: ${MaterialTheme.colorScheme.primary}")
        Text("Background: ${MaterialTheme.colorScheme.background}")
    }
}

@Preview
@Composable
fun CheckDefaultColorsPr() {
    CheckDefaultColors()
}