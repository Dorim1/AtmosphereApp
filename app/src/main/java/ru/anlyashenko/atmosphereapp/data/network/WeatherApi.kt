package ru.anlyashenko.atmosphereapp.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

fun WeatherApi (
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
) : WeatherApi {
    val retrofit = retrofit(baseUrl, okHttpClient)
    return retrofit.create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
) : Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .build()

}