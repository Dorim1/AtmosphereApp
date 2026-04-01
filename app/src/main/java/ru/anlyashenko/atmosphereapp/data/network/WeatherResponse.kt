package ru.anlyashenko.atmosphereapp.data.network

import android.health.connect.datatypes.units.Temperature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current_weather")
    val currentWeather: CurrentWeather,
    @SerialName("hourly")
    val hourly: Hourly
)

@Serializable
data class CurrentWeather(
    @SerialName("temperature")
    val temperature: Double,
    @SerialName("weathercode")
    val weatherCode: Int
)

@Serializable
data class Hourly (
    @SerialName("time")
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature: List<Double>,
    @SerialName("weathercode")
    val weatherCode: List<Int>
)
