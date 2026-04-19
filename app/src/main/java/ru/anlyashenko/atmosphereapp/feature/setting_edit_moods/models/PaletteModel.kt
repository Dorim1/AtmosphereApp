package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class PaletteModel(
    val id: Int,
    @param:StringRes val nameRes: Int,
    val colors: List<Color>
)