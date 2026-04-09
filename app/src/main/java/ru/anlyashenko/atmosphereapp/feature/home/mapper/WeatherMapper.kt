package ru.anlyashenko.atmosphereapp.feature.home.mapper

import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.data.network.models.WeatherResponseDTO
import ru.anlyashenko.atmosphereapp.feature.home.models.HourlyWeatherUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel
import kotlin.math.roundToInt

private fun getWeatherDescAndIcon(weatherCode: Int): Pair<Int, Int> {
    return when (weatherCode) {
        0 -> R.string.weather_clear to R.drawable.ic_weather_clear_sky
        1, 2 -> R.string.weather_partly_cloudy to R.drawable.ic_weather_partly_cloudy_day
        3 -> R.string.weather_cloudy to R.drawable.ic_weather_cloudy
        45, 48 -> R.string.weather_foggy to R.drawable.ic_weather_foggy
        51, 53, 55, 56, 57 -> R.string.weather_drizzle to R.drawable.ic_weather_rainy
        61, 63, 65, 66, 67, 80, 81, 82 -> R.string.weather_rain to R.drawable.ic_weather_rainy
        71, 73, 75, 77, 85, 86 -> R.string.weather_snow to R.drawable.ic_weather_snowy
        95 -> R.string.weather_thunderstorm to R.drawable.ic_weather_thunderstorm
        96, 99 -> R.string.weather_hail to R.drawable.ic_weather_hail
        else -> R.string.weather_unknown to R.drawable.ic_weather_question_mark
    }
}

fun WeatherResponseDTO.toUiModel(cityName: String): WeatherUiModel {
    val tempInt = this.currentWeather.temperature.roundToInt()
    val tempString = if (tempInt > 0) "+$tempInt°" else "$tempInt°"

    val (desc, icon) = getWeatherDescAndIcon(this.currentWeather.weatherCode)

    val currentIndex = this.hourly.time.indexOfFirst { it >= this.currentWeather.time }

    val startIndex = if (currentIndex != -1) currentIndex else 0

    val targetIndices = listOf(startIndex + 4, startIndex + 8, startIndex + 12)

    val hourlyList = targetIndices.mapNotNull { index ->
        if (index < this.hourly.time.size) {
            val timeString = this.hourly.time[index]
            val temp = this.hourly.temperature[index].roundToInt()
            val code = this.hourly.weatherCode[index]

            val (_, hourlyIcon) = getWeatherDescAndIcon(
                code
            )

            HourlyWeatherUiModel(
                time = timeString.takeLast(5),
                temperature = if (temp > 0) "+$temp°" else "$temp°",
                iconResId = hourlyIcon
            )
        } else {
            null
        }

    }

    return WeatherUiModel(
        cityName = cityName,
        temperature = tempString,
        descriptionRes = desc,
        iconResId = icon,
        hourlyForecast = hourlyList
    )
}