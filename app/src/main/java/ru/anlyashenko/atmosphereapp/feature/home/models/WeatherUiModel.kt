package ru.anlyashenko.atmosphereapp.feature.home.models

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