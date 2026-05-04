package ru.anlyashenko.core.data.location

import android.util.Log
import ru.anlyashenko.core.model.UserLocation
import ru.anlyashenko.network.IpGeoApi
import javax.inject.Inject

class DefaultLocationTracker @Inject constructor(
    private val ipGeoApi: IpGeoApi
) : LocationTracker {

    override suspend fun getCurrentLocation(): UserLocation? {
        @Suppress("TooGenericExceptionCaught")
        return try {
            val response = ipGeoApi.getLocationByIp()
            UserLocation(
                latitude = response.latitude,
                longitude = response.longitude,
                city = response.city
            )
        } catch (e: Exception) {
            Log.e("DefaultLocationTracker", "Failed to get location", e)
            null
        }
    }
}
