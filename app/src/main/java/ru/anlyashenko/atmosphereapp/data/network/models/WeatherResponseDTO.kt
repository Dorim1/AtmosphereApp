package ru.anlyashenko.atmosphereapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDTO(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDTO,
    @SerialName("hourly")
    val hourly: HourlyDTO
)