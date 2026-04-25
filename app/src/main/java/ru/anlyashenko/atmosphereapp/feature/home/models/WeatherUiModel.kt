package ru.anlyashenko.atmosphereapp.feature.home.models

import androidx.annotation.StringRes

// todo: ----
data class WeatherUiModel(
    val cityName: String,
    val temperature: String,
    @param:StringRes val descriptionRes: Int,
    val iconResId: Int,
    val hourlyForecast: List<HourlyWeatherUiModel>
)

data class HourlyWeatherUiModel(
    val time: String,
    val temperature: String,
    val iconResId: Int
)