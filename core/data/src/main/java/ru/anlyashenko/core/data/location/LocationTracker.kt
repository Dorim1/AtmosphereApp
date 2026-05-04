package ru.anlyashenko.core.data.location

import ru.anlyashenko.core.model.UserLocation

interface LocationTracker {
    suspend fun getCurrentLocation(): UserLocation?
}
