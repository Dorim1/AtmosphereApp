package ru.anlyashenko.atmosphereapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDTO (
    @SerialName("time")
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature: List<Double>,
    @SerialName("weathercode")
    val weatherCode: List<Int>
)