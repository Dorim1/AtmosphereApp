package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class MoodEditModel(
    val id: Int,
    val name: String,
    val color: Color,
    @param:DrawableRes val iconRes: Int
)