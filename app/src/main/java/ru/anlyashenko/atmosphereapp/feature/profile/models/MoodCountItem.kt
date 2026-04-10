package ru.anlyashenko.atmosphereapp.feature.profile.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class MoodCountItem(
    @param:StringRes val name: Int,
    val count: Int,
    val color: Color
)
