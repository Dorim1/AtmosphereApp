package ru.anlyashenko.feature.settings.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.icons.MoodIconManager
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.core.model.Mood

// todo: Вынести MoodUiModel в :core:designsystem и работать с ней в двух фичах?

data class SettingsMoodUiModel(
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

fun Mood.toUiModel(activePalette: PaletteModel): SettingsMoodUiModel {
    val defaultLabelRes = when (this.level) {
        5 -> R.string.mood_excellent
        4 -> R.string.mood_good
        3 -> R.string.mood_normal
        2 -> R.string.mood_bad
        1 -> R.string.mood_terrible
        else -> R.string.mood_normal
    }

    val baseColor = Color(this.colorHex.toColorInt())

    val colorIndex = activePalette.colors.size - this.level
    val dynamicColor = activePalette.colors.getOrElse(colorIndex) { baseColor }

    return SettingsMoodUiModel(
        id = this.id,
        level = this.level,
        defaultLabelRes = defaultLabelRes,
        customLabel = this.customName,
        iconRes = MoodIconManager.getIconRes(this.iconKey),
        color = dynamicColor
    )
}
