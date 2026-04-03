package ru.anlyashenko.atmosphereapp.domain.location

import ru.anlyashenko.atmosphereapp.domain.model.UserLocation

interface LocationTracker {
    suspend fun getCurrentLocation(): UserLocation?
}