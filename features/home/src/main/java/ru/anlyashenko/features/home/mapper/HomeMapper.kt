package ru.anlyashenko.features.home.mapper

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.icons.MoodIconManager
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.model.DiaryRecord
import ru.anlyashenko.core.model.Mood
import ru.anlyashenko.core.model.Weather
import ru.anlyashenko.features.home.model.DiaryRecordUiModel
import ru.anlyashenko.features.home.model.HourlyWeatherUiModel
import ru.anlyashenko.features.home.model.MoodUiModel
import ru.anlyashenko.features.home.model.WeatherUiModel
import kotlin.math.roundToInt

private fun getWeatherDescAndIcon(weatherCode: Int): Pair<Int, Int> {
    return when (weatherCode) {
        0 -> R.string.weather_clear to R.drawable.ic_weather_clear_sky
        1, 2 -> R.string.weather_partly_cloudy to R.drawable.ic_weather_partly_cloudy_day
        3 -> R.string.weather_cloudy to R.drawable.ic_weather_cloudy
        45, 48 -> R.string.weather_foggy to R.drawable.ic_weather_foggy
        51, 53, 55, 56, 57 -> R.string.weather_drizzle to R.drawable.ic_weather_rainy
        61, 63, 65, 66, 67, 80, 81, 82 -> R.string.weather_rain to R.drawable.ic_weather_rainy
        71, 73, 75, 77, 85, 86 -> R.string.weather_snow to R.drawable.ic_weather_snowy
        95 -> R.string.weather_thunderstorm to R.drawable.ic_weather_thunderstorm
        96, 99 -> R.string.weather_hail to R.drawable.ic_weather_hail
        else -> R.string.weather_unknown to R.drawable.ic_weather_question_mark
    }
}

fun Weather.toUiModel(): WeatherUiModel {
    val tempInt = this.temperature.roundToInt()
    val tempString = if (tempInt > 0) "+$tempInt°" else "$tempInt°"

    val (desc, icon) = getWeatherDescAndIcon(this.weatherCode)

    val hourlyUiList = this.hourlyForecast.map { hourly ->
        val hourlyTempInt = hourly.temperature.roundToInt()
        val (_, hourlyIcon) = getWeatherDescAndIcon(hourly.weatherCode)

        HourlyWeatherUiModel(
            time = hourly.time.takeLast(5),
            temperature = if (hourlyTempInt > 0) "+$hourlyTempInt°" else "$hourlyTempInt°",
            iconResId = hourlyIcon
        )
    }

    return WeatherUiModel(
        cityName = this.cityName,
        temperature = tempString,
        descriptionRes = desc,
        iconResId = icon,
        hourlyForecast = hourlyUiList
    )
}

fun Mood.toUiModel(activePalette: PaletteModel): MoodUiModel {
    val defaultLabelRes = when (this.level) {
        5 -> R.string.mood_excellent
        4 -> R.string.mood_good
        3 -> R.string.mood_normal
        2 -> R.string.mood_bad
        1 -> R.string.mood_terrible
        else -> R.string.mood_normal
    }

    val baseColor = Color(this.colorHex.toColorInt())

    val colorIndex = activePalette.colors.size - this.level
    val dynamicColor = activePalette.colors.getOrElse(colorIndex) { baseColor }

    return MoodUiModel(
        id = this.id,
        level = this.level,
        defaultLabelRes = defaultLabelRes,
        customLabel = this.customName,
        iconRes = MoodIconManager.getIconRes(this.iconKey),
        color = dynamicColor
    )
}

fun DiaryRecord.toUiModel(mappedMoods: List<MoodUiModel>): DiaryRecordUiModel {
    val moodUiModel = mappedMoods.find { it.id == this.mood?.id }
    return DiaryRecordUiModel(
        date = this.date,
        mood = moodUiModel,
        note = this.note
    )
}