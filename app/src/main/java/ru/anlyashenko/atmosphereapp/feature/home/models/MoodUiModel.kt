package ru.anlyashenko.atmosphereapp.feature.home.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class MoodUiModel(
    val id: Int,
    val label: String,
    val level: Int,
    @param:DrawableRes val iconRes: Int,
    val color: Color
)
