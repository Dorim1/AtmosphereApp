package ru.anlyashenko.atmosphereapp.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import ru.anlyashenko.atmosphereapp.data.network.models.IpGeoResponseDTO

interface IpGeoApi {
    @GET("json/")
    suspend fun getLocationByIp(): IpGeoResponseDTO
}

fun IpGeoApi (
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json
) : IpGeoApi {
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    json: Json
) : Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .build()

}