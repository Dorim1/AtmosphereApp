package ru.anlyashenko.atmosphereapp.feature.profile.models

import androidx.compose.ui.graphics.Color

data class DailyMoodStat(
    val dayName: String,
    val level: Int,
    val color: Color
)