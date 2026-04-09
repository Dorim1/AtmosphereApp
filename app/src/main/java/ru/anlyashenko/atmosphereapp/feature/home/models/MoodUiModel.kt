package ru.anlyashenko.atmosphereapp.feature.home.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class MoodUiModel(
    val id: Int,
    val level: Int,
    @param:StringRes val label: Int,
    @param:DrawableRes val iconRes: Int,
    val color: Color
)
