package ru.anlyashenko.atmosphereapp.feature.yearly_stats.mapper

import androidx.compose.ui.graphics.Color

fun getMoodNameFromColor(color: Color) : String {
    // TODO: Сделать класс Mood
    return when (color) {
        Color(0xFF8AA232) -> "Отлично" // Пример зеленого
        Color(0xFF0A6C60) -> "Хорошо"  // Пример темно-зеленого
        Color(0xFFFFC107) -> "Нормально" // Пример желтого
        Color(0xFFFF5722) -> "Плохо"     // Пример оранжевого
        Color(0xFFD32F2F) -> "Ужасно"    // Пример красного
        else -> "Эмоция"
    }
}

fun getLightColorForBackground(color: Color): Color {
    return color.copy(alpha = 0.2f)
}