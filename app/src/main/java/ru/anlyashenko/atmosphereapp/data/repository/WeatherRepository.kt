package ru.anlyashenko.atmosphereapp.data.repository

import android.util.Log
import kotlinx.coroutines.withContext
import ru.anlyashenko.atmosphereapp.core.dispathchers.DefaultDispatcherProvider
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.data.network.WeatherApi
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.toUiModel

class WeatherRepository(
    private val api: WeatherApi,
    private val ioDispatcher: DefaultDispatcherProvider
) {
    suspend fun getWeather(lat: Double, lon: Double) :Result<WeatherUiModel> {
        return withContext(ioDispatcher.io) {
            try {
                val response = api.getCurrentWeather(lat, lon)
                Log.d("Weather", "Response: $response")
                Result.Success(response.toUiModel())
            } catch (e: Exception) {
                Log.e("Weather", "Error: ${e.message}", e)
                Result.Error(Exception("Не удалось получить погоду"))
            }
        }
    }
}