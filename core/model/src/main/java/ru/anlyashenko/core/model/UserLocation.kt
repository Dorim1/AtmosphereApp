package ru.anlyashenko.core.model

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val city: String? = null,
)