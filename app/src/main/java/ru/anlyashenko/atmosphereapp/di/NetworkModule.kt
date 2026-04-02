package ru.anlyashenko.atmosphereapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import ru.anlyashenko.atmosphereapp.core.dispathchers.DefaultDispatcherProvider
import ru.anlyashenko.atmosphereapp.core.dispathchers.DispatcherProvider
import ru.anlyashenko.atmosphereapp.data.network.WeatherApi
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.open-meteo.com/"

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi(
            baseUrl = BASE_URL,
            json = Json { ignoreUnknownKeys = true }
        )
    }

}