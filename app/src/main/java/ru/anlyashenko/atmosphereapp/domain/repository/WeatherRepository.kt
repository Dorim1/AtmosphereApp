package ru.anlyashenko.atmosphereapp.domain.repository

import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, cityName: String): Result<WeatherUiModel>
}