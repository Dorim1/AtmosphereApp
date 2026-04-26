package ru.anlyashenko.features.home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import ru.anlyashenko.core.designsystem.util.UiText

data class MoodUiModel(
    val id: Int,
    val level: Int,
    @param:StringRes val defaultLabelRes: Int,
    val customLabel: String?,
    @param:DrawableRes val iconRes: Int,
    val color: Color
) {
    val displayName: UiText
        get() = if (customLabel.isNullOrBlank()) {
            UiText.StringResource(defaultLabelRes)
        } else {
            UiText.DynamicString(customLabel)
        }
}