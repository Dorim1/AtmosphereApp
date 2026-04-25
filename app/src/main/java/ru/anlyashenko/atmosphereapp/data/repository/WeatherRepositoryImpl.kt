package ru.anlyashenko.atmosphereapp.data.repository

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

// todo: ----
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
): WeatherRepository {

    private var cachedWeather: WeatherUiModel? = null
    private var lastFetchTime: Long = 0

    override suspend fun getWeather(lat: Double, lon: Double, cityName: String) :Result<WeatherUiModel> {
        val currentTime = System.currentTimeMillis()
        val isCacheValid = (currentTime - lastFetchTime) < CACHE_VALID_DURATION_MS

        if (isCacheValid && cachedWeather != null) {
            return Result.Success(cachedWeather!!)
        }

        return withContext(ioDispatcher) {
            try {
                val response = api.getCurrentWeather(lat, lon)
                val uiModel = response.toUiModel(cityName)

                cachedWeather = uiModel
                lastFetchTime = System.currentTimeMillis()

                Result.Success(uiModel)
            } catch (e: Exception) {
                Result.Error(Exception("Не удалось получить погоду", e))
            }
        }
    }
}

private const val CACHE_VALID_DURATION_MS = 30 * 60 * 1000
