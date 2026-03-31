package ru.anlyashenko.atmosphereapp.domain.model

import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.data.remote.network.WeatherResponse
import kotlin.math.roundToInt

data class WeatherUiModel(
    val temperature: String,
    val description: String,
    val iconResultId: Int
)

fun WeatherResponse.toUiModel(): WeatherUiModel {
    val tempInt = this.currentWeather.temperature.roundToInt()
    val tempString = if (tempInt > 0) "+$tempInt°" else "$tempInt°" // TODO: МБ -

    val (desc, icon) = when (this.currentWeather.weatherCode) {
        0 -> "Ясно" to R.drawable.ic_weather_clear_sky
        1, 2, 3 -> "Облачно" to R.drawable.ic_weather_partly_cloudy_day
        45, 48 -> "Туман" to R.drawable.ic_weather_foggy
        51, 53, 55, 56, 57 -> "Морось" to R.drawable.ic_weather_rainy
        61, 63, 65, 66, 67 -> "Дождь" to R.drawable.ic_weather_rainy
        71, 73, 75, 77 -> "Снег" to R.drawable.ic_weather_snowy
        95 -> "Гроза" to R.drawable.ic_weather_thunderstorm
        96, 99 -> "Град" to R.drawable.ic_weather_hail
        else -> "Неизвестно" to R.drawable.ic_weather_question_mark
    }

    return WeatherUiModel(
        temperature = tempString,
        description = desc,
        iconResultId = icon
    )
}