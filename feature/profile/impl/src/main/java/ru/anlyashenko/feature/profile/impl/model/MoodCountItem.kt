package ru.anlyashenko.feature.profile.impl.model

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.core.designsystem.util.UiText

data class MoodCountItem(
    val name: UiText,
    val count: Int,
    val color: Color
)
