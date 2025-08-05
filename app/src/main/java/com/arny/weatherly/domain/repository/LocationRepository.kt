package com.arny.weatherly.domain.repository

import com.arny.weatherly.domain.model.Location

interface LocationRepository {
    suspend fun getLocation(): Result<Location>
}