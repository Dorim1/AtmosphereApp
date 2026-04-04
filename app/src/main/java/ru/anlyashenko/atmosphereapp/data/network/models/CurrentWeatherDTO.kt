package ru.anlyashenko.atmosphereapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    @SerialName("temperature")
    val temperature: Double,
    @SerialName("weathercode")
    val weatherCode: Int,
    @SerialName("time")
    val time: String
)
