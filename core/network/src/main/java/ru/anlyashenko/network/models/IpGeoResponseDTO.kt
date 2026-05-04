package ru.anlyashenko.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.anlyashenko.core.model.UserLocation

@Serializable
data class IpGeoResponseDTO(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("city") val city: String
)

fun IpGeoResponseDTO.asExternalModel(): UserLocation = UserLocation(
    latitude = longitude,
    longitude = longitude,
    city = city
)
