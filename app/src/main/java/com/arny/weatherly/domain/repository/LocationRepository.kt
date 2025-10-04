package com.arny.weatherly.domain.repository

import com.arny.weatherly.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocation(): Flow<Result<Location>>
    suspend fun searchCity(name: String): Result<List<Location>>
}