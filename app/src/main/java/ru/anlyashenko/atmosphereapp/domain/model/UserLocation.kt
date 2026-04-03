package ru.anlyashenko.atmosphereapp.domain.model

data class UserLocation(
    val lat: Double,
    val lon: Double,
    val city: String? = null
)
