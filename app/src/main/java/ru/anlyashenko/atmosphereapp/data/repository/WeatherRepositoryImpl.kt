package ru.anlyashenko.atmosphereapp.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.data.network.WeatherApi
import ru.anlyashenko.atmosphereapp.di.IoDispatcher
import ru.anlyashenko.atmosphereapp.domain.repository.WeatherRepository
import ru.anlyashenko.atmosphereapp.feature.home.mapper.toUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel
import javax.inject.Inject
import javax.inject.Singleton

// todo: Удалить логи
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
): WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double, cityName: String) :Result<WeatherUiModel> {
        return withContext(ioDispatcher) {
            try {
                val response = api.getCurrentWeather(lat, lon)
                Log.d("Weather", "Response: $response")
                Result.Success(response.toUiModel(cityName))
            } catch (e: Exception) {
                Log.e("Weather", "Error: ${e.message}", e)
                Result.Error(Exception("Не удалось получить погоду"))
            }
        }
    }
}