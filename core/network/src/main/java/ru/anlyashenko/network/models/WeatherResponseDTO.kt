package ru.anlyashenko.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.anlyashenko.core.model.HourlyWeather
import ru.anlyashenko.core.model.Weather

@Serializable
data class WeatherResponseDTO(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDTO,
    @SerialName("hourly")
    val hourly: HourlyDTO
)

fun WeatherResponseDTO.asExternalModel(cityName: String): Weather {
    val currentIndex = this.hourly.time.indexOfFirst { it >= this.currentWeather.time }
    val startIndex = if (currentIndex != -1) currentIndex else 0
    val targetIndices = listOf(startIndex + 4, startIndex + 8, startIndex + 12)

    val hourlyList = targetIndices.mapNotNull { index ->
        if (index < this.hourly.time.size) {
            HourlyWeather(
                time = this.hourly.time[index],
                temperature = this.hourly.temperature[index],
                weatherCode = this.hourly.weatherCode[index]
            )
        } else {
            null
        }
    }

    return Weather(
        cityName = cityName,
        temperature = this.currentWeather.temperature,
        weatherCode = this.currentWeather.weatherCode,
        hourlyForecast = hourlyList
    )
}