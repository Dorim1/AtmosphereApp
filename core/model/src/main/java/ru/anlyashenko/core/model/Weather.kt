package ru.anlyashenko.core.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val weatherCode: Int,
    val hourlyForecast: List<HourlyWeather>
)
