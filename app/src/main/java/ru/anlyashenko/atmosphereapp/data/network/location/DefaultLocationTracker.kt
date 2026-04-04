package ru.anlyashenko.atmosphereapp.data.network.location

import ru.anlyashenko.atmosphereapp.data.network.IpGeoApi
import ru.anlyashenko.atmosphereapp.domain.location.LocationTracker
import ru.anlyashenko.atmosphereapp.domain.model.UserLocation
import javax.inject.Inject

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

