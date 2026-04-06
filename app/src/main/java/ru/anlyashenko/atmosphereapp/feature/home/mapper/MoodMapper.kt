package ru.anlyashenko.atmosphereapp.feature.home.mapper

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import androidx.core.graphics.toColorInt
import ru.anlyashenko.atmosphereapp.core.utils.MoodIconManager

fun MoodDBO.toUiModel(): MoodUiModel {
    // TODO: Цвета положить в Color.kt
    return MoodUiModel(
        id = this.id,
        label = this.label,
        level = this.level,
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

