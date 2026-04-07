package ru.anlyashenko.atmosphereapp.feature.settings.theme

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.domain.model.MoodPalette

object MoodThemeProvider {

    // TODO: запихнуть цвета в тему
    fun getColor(level: Int, palette: MoodPalette): Color {
        return when (palette) {
            MoodPalette.CLASSIC -> when (level) {
                1 -> Color(0xFF8AA232) // Отлично
                2 -> Color(0xFF329340) // Хорошо
                3 -> Color(0xFFFBC117) // Нормально
                4 -> Color(0xFFFB5E01) // Плохо
                5 -> Color(0xFFE40000) // Ужасно
                else -> Color.Gray
            }

            MoodPalette.PASTEL -> when (level) {
                1 -> Color(0xFFA8E6CF) // Нежно-зеленый
                2 -> Color(0xFFDCEDC1)
                3 -> Color(0xFFFFD3B6)
                4 -> Color(0xFFFFAAA5)
                5 -> Color(0xFFFF8B94) // Нежно-красный
                else -> Color.Gray
            }

            else -> Color.Gray
        }
    }
}