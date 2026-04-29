package ru.anlyashenko.feature.profile.model

import androidx.compose.ui.graphics.Color
import java.time.DayOfWeek

data class DailyMoodStat(
    val dayOfWeek: DayOfWeek,
    val level: Float,
    val color: Color
)