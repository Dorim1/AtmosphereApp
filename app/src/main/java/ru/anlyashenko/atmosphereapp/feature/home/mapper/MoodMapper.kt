package ru.anlyashenko.atmosphereapp.feature.home.mapper

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import androidx.core.graphics.toColorInt
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.utils.MoodIconManager

fun MoodDBO.toUiModel(): MoodUiModel {
    val labelResId = when (this.level) {
        1 -> R.string.mood_excellent
        2 -> R.string.mood_good
        3 -> R.string.mood_normal
        4 -> R.string.mood_bad
        5 -> R.string.mood_terrible
        else -> R.string.mood_normal
    }

    // TODO: Цвета положить в Color.kt
    return MoodUiModel(
        id = this.id,
        level = this.level,
        label = labelResId,
        iconRes = MoodIconManager.getIconRes(this.iconKey),
        color = Color(colorHex.toColorInt())
    )
}

fun Color.toHexCode(): String {
    return String.format("#%02X%02X%02X%02X",
        (this.alpha * 255).toInt(),
        (this.red * 255).toInt(),
        (this.green * 255).toInt(),
        (this.blue * 255).toInt(),
    )
}

