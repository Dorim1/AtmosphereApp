package ru.anlyashenko.atmosphereapp.feature.home.models

import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.data.network.WeatherResponse
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt

data class WeatherUiModel(
    val cityName: String,
    val temperature: String,
    val description: String,
    val iconResId: Int,
    val hourlyForecast: List<HourlyWeatherUiModel>
)

data class HourlyWeatherUiModel(
    val time: String,
    val temperature: String,
    val iconResId: Int
)

private fun getWeatherDescAndIcon(weatherCode: Int): Pair<String, Int> {
    return when (weatherCode) {
        0 -> "Ясно" to R.drawable.ic_weather_clear_sky
        1, 2, 3 -> "Облачно" to R.drawable.ic_weather_partly_cloudy_day
        45, 48 -> "Туман" to R.drawable.ic_weather_foggy
        51, 53, 55, 56, 57 -> "Морось" to R.drawable.ic_weather_rainy
        61, 63, 65, 66, 67, 80, 81, 82 -> "Дождь" to R.drawable.ic_weather_rainy // TODO: 80, 81 и 82 посмотреть иконки
            71, 73, 75, 77, 85, 86 -> "Снег" to R.drawable.ic_weather_snowy // TODO: 85 и 86 посмотреть иконки
        95 -> "Гроза" to R.drawable.ic_weather_thunderstorm
        96, 99 -> "Град" to R.drawable.ic_weather_hail
        else -> "Неизвестно" to R.drawable.ic_weather_question_mark // TODO: Заменить на --
    }
}

fun WeatherResponse.toUiModel(cityName: String): WeatherUiModel {
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

            val (_, hourlyIcon) = getWeatherDescAndIcon(code)

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
        description = desc,
        iconResId = icon,
        hourlyForecast = hourlyList
    )
}