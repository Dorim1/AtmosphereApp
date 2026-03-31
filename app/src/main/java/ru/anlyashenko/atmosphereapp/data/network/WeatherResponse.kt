package ru.anlyashenko.atmosphereapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current_weather")
    val currentWeather: CurrentWeather
)

@Serializable
data class CurrentWeather(
    @SerialName("temperature")
    val temperature: Double,
    @SerialName("weathercode")
    val weatherCode: Int
)

