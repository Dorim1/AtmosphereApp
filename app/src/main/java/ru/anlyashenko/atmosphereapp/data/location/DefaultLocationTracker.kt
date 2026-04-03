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
//    private val locationClient: FusedLocationProviderClient,
//    private val application: Application
    private val ipGeoApi: IpGeoApi
) : LocationTracker {

    /*override suspend fun getCurrentLocation(): Location? {
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasCoarseLocationPermission || !isGpsEnabled) {
            Log.d("Location", "No permission or GPS disabled")
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful && result != null) {
                        Log.d("Location", "lastLocation success: ${result.latitude}")
                        cont.resume(result)
                    } else {
//                        cont.resume(null)
                        requestFreshLocation(cont)
                    }
                    return@apply
                }
//                addOnSuccessListener { cont.resume(it) }
                addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d("Location", "lastLocation listener: ${location.latitude}")
                        cont.resume(location)
                    } else {
                        requestFreshLocation(cont)
                    }
                }
                addOnFailureListener {
                    Log.e("Location", "lastLocation failed: ${it.message}")
                    cont.resume(null)
                }
                addOnCanceledListener { cont.resume(null) }
            }
        }
    }*/

    /*  @SuppressLint("MissingPermission")
    private fun requestFreshLocation(cont: CancellableContinuation<Location?>) {
        Log.d("Location", "Requesting fresh location...")
        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setMaxUpdateAgeMillis(60_000L)
            .build()

        locationClient.getCurrentLocation(request, null)
            .addOnSuccessListener { location ->
                Log.d("Location", "Fresh location: ${location?.latitude}")
                cont.resume(location)
            }
            .addOnFailureListener {
                Log.e("Location", "Fresh location failed: ${it.message}")
                cont.resume(null)
            }
    }*/

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

