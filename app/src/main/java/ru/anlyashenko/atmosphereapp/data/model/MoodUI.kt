package ru.anlyashenko.atmosphereapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MoodUI(
    val id: Int,
    val title: String,
    val backgroundColor: Color,
    val icon: ImageVector
)
