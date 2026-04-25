package ru.anlyashenko.core.data.repository

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, cityName: String): Result<Weather>
}