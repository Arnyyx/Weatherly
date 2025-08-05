package com.arny.weatherly.data.remote

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.domain.repository.LocationRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    private val context: Context
) : LocationRepository {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun getLocation(): Result<Location> {
        return try {
            val location = getCurrentLocation() ?: getLastKnownLocation()
            if (location != null) {
                Result.success(location)
            } else {
                Result.failure(Exception("Unable to retrieve location"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Lấy vị trí hiện tại với getCurrentLocation
    private suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    continuation.resume(null)
                    return@suspendCancellableCoroutine
                }

                // Tạo CurrentLocationRequest với các tùy chọn
                val locationRequest = CurrentLocationRequest.Builder()
                    .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY) // Độ chính xác
                    .setDurationMillis(3000) // Timeout 3 giây
                    .setMaxUpdateAgeMillis(60000) // Chấp nhận cache trong 1 phút
                    .build()

                val cancellationTokenSource = CancellationTokenSource()
                val cancellationToken: CancellationToken = cancellationTokenSource.token

                continuation.invokeOnCancellation {
                    cancellationTokenSource.cancel()
                }

                fusedLocationClient.getCurrentLocation(locationRequest, cancellationToken)
                    .addOnSuccessListener { location: android.location.Location? ->
                        if (location != null) {
                            val userLocation =
                                getAddressFromLocation(location.latitude, location.longitude)
                            continuation.resume(userLocation)
                        } else {
                            continuation.resume(null)
                        }
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(null)
                    }
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }

    private suspend fun getLastKnownLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    continuation.resume(null)
                    return@suspendCancellableCoroutine
                }

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: android.location.Location? ->
                        if (location != null) {
                            val userLocation =
                                getAddressFromLocation(location.latitude, location.longitude)
                            continuation.resume(userLocation)
                        } else {
                            continuation.resume(null)
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }

    // Chuyển đổi tọa độ thành địa chỉ
    private fun getAddressFromLocation(latitude: Double, longitude: Double): Location {
        return try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                Location(
                    ward = address.subAdminArea ?: "",
                    city = address.locality ?: address.adminArea ?: "",
                    country = address.countryName ?: "",
                    fullAddress = address.getAddressLine(0) ?: "",
                    latitude = latitude,
                    longitude = longitude
                )
            } else {
                Location(latitude = latitude, longitude = longitude)
            }
        } catch (e: Exception) {
            Location(latitude = latitude, longitude = longitude)
        }
    }
}