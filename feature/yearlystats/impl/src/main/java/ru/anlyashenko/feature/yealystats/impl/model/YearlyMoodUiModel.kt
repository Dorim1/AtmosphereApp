package ru.anlyashenko.feature.yealystats.impl.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.core.model.Mood

data class YearlyMoodUiModel(
    val id: Int,
    val level: Int,
    @param:StringRes val defaultLabelRes: Int,
    val customLabel: String?,
    val color: Color
) {
    // todo: Название не меняется на кастомнейм в карточка процентажа
    val displayName: UiText
        get() = if (customLabel.isNullOrBlank()) {
            UiText.StringResource(defaultLabelRes)
        } else {
            UiText.DynamicString(customLabel)
        }
}

fun Mood.toUiModel(activePalette: PaletteModel): YearlyMoodUiModel {
    val baseColor = Color(this.colorHex.toColorInt())
    val colorIndex = activePalette.colors.size - this.level
    val dynamicColor = activePalette.colors.getOrElse(colorIndex) { baseColor }

    val defaultLabelRes = when (this.level) {
        5 -> R.string.mood_excellent
        4 -> R.string.mood_good
        3 -> R.string.mood_normal
        2 -> R.string.mood_bad
        1 -> R.string.mood_terrible
        else -> R.string.mood_normal
    }

    return YearlyMoodUiModel(
        id = this.id,
        level = this.level,
        defaultLabelRes = defaultLabelRes,
        customLabel = this.customName,
        color = dynamicColor
    )
}