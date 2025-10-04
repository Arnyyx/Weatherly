package com.arny.weatherly.presentation.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.viewmodels.LocationViewModel
import com.arny.weatherly.presentation.viewmodels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
) {
}
