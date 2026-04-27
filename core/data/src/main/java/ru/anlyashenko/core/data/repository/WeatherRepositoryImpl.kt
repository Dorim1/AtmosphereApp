package ru.anlyashenko.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.anlyashenko.core.common.di.IoDispatcher
import ru.anlyashenko.core.model.Weather
import ru.anlyashenko.network.WeatherApi
import ru.anlyashenko.network.models.asExternalModel
import ru.anlyashenko.core.common.util.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    private var cachedWeather: Weather? = null
    private var lastFetchTime: Long = 0

    override suspend fun getWeather(lat: Double, lon: Double, cityName: String): Result<Weather> {
        val currentTime = System.currentTimeMillis()
        val isCacheValid = (currentTime - lastFetchTime) < CACHE_VALID_DURATION_MS

        if (isCacheValid && cachedWeather != null) {
            return Result.Success(cachedWeather!!)
        }

        return withContext(ioDispatcher) {
            try {
                val responseDTO = api.getCurrentWeather(lat, lon)
                val domainModel = responseDTO.asExternalModel(cityName)

                cachedWeather = domainModel
                lastFetchTime = System.currentTimeMillis()

                Result.Success(domainModel)
            } catch (e: Exception) {
                Result.Error(Exception("Couldn't get the weather", e))
            }
        }
    }

    private companion object {
        const val  CACHE_VALID_DURATION_MS = 30 * 60 * 1000
    }

}