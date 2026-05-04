package ru.anlyashenko.core.data.repository

import ru.anlyashenko.core.common.util.Result
import ru.anlyashenko.core.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, cityName: String): Result<Weather>
}
