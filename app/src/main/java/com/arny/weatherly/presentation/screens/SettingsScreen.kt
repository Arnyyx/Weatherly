package com.arny.weatherly.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit = {}
) {
    var temperatureUnit by remember { mutableStateOf("°C") }
    var windSpeedUnit by remember { mutableStateOf("Kilometres per hour (km/h)") }
    var pressureUnit by remember { mutableStateOf("Millibar (mbar)") }
    var updateAtNight by remember { mutableStateOf(false) }

    var showTemperatureDropdown by remember { mutableStateOf(false) }
    var showWindSpeedDropdown by remember { mutableStateOf(false) }
    var showPressureDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Units Section
            SectionHeader(title = "Units")

            Spacer(modifier = Modifier.height(16.dp))

            // Temperature units
            DropdownSettingItem(
                title = "Temperature units",
                selectedValue = temperatureUnit,
                options = listOf("°C", "°F"),
                expanded = showTemperatureDropdown,
                onExpandedChange = { showTemperatureDropdown = it },
                onSelectionChange = { temperatureUnit = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Wind speed units
            DropdownSettingItem(
                title = "Wind speed units",
                selectedValue = windSpeedUnit,
                options = listOf(
                    "Kilometres per hour (km/h)",
                    "Miles per hour (mph)",
                    "Metres per second (m/s)",
                    "Knots"
                ),
                expanded = showWindSpeedDropdown,
                onExpandedChange = { showWindSpeedDropdown = it },
                onSelectionChange = { windSpeedUnit = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Atmospheric pressure units
            DropdownSettingItem(
                title = "Atmospheric pressure units",
                selectedValue = pressureUnit,
                options = listOf(
                    "Millibar (mbar)",
                    "Inches of mercury (inHg)",
                    "Hectopascal (hPa)",
                    "Kilopascal (kPa)"
                ),
                expanded = showPressureDropdown,
                onExpandedChange = { showPressureDropdown = it },
                onSelectionChange = { pressureUnit = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Other settings Section
            SectionHeader(title = "Other settings")

            Spacer(modifier = Modifier.height(16.dp))

            // Update at night automatically
            SwitchSettingItem(
                title = "Update at night automatically",
                subtitle = "Update weather info between 23:00 and 07:00",
                checked = updateAtNight,
                onCheckedChange = { updateAtNight = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // About Weather Section
            SectionHeader(title = "About Weather")

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback
            NavigationSettingItem(
                title = "Feedback",
                onClick = { /* Handle feedback */ }
            )

            // Privacy Policy
            NavigationSettingItem(
                title = "Privacy Policy",
                onClick = { /* Handle privacy policy */ }
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSettingItem(
    title: String,
    selectedValue: String,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSelectionChange: (String) -> Unit
) {
    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .menuAnchor()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedValue,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectionChange(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchSettingItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun NavigationSettingItem(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )

        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate to $title",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}