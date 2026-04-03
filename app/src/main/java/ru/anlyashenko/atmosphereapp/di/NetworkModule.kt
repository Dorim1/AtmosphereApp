package ru.anlyashenko.atmosphereapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import ru.anlyashenko.atmosphereapp.data.network.IpGeoApi
import ru.anlyashenko.atmosphereapp.data.network.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"
    private const val IP_BASE_URL = "https://ipwhois.app/"

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi(
            baseUrl = WEATHER_BASE_URL,
            json = Json { ignoreUnknownKeys = true }
        )
    }

    @Provides
    @Singleton
    fun provideIpGeoApi(): IpGeoApi {
        return IpGeoApi(
            baseUrl = IP_BASE_URL,
            json = Json { ignoreUnknownKeys = true }
        )
    }

}