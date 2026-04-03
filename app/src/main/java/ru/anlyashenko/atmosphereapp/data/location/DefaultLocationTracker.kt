package ru.anlyashenko.atmosphereapp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.anlyashenko.atmosphereapp.data.network.IpGeoApi
import ru.anlyashenko.atmosphereapp.domain.location.LocationTracker
import ru.anlyashenko.atmosphereapp.domain.model.UserLocation
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val ipGeoApi: IpGeoApi
) : LocationTracker {

    override suspend fun getCurrentLocation(): UserLocation? {
        return try {
            val response = ipGeoApi.getLocationByIp()
            UserLocation(
                lat = response.latitude,
                lon = response.longitude,
                city = response.city
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

