package ru.anlyashenko.core.model

data class HourlyWeather(
    val time: String,
    val temperature: Double,
    val weatherCode: Int
)

