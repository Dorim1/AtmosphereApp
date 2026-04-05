package ru.anlyashenko.atmosphereapp.feature.home.mapper

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import androidx.core.graphics.toColorInt

fun MoodDBO.toUiModel(): MoodUiModel {
    // TODO: Цвета положить в Color.kt
    return MoodUiModel(
        id = this.id,
        label = this.label,
        level = this.level,
        iconRes = iconKey.toIconResId(),
        color = Color(colorHex.toColorInt())
    )
}

// TODO: Перенести в core/utils
fun String.toIconResId(): Int = when (this) {
    "ic_mood_very_satisfied" -> R.drawable.ic_mood_very_satisfied
    "ic_mood_satisfied" -> R.drawable.ic_mood_satisfied
    "ic_mood_neutral" -> R.drawable.ic_mood_neutral
    "ic_mood_dissatisfied" -> R.drawable.ic_mood_dissatisfied
    "ic_mood_very_dissatisfied" -> R.drawable.ic_mood_very_dissatisfied
    else -> R.drawable.ic_mood_neutral
}

