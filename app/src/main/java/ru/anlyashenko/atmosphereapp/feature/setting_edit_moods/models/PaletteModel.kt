package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models

import androidx.compose.ui.graphics.Color

data class PaletteModel(
    val id: Int,
    val name: String,
    val colors: List<Color>
)