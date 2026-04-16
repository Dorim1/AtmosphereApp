package ru.anlyashenko.atmosphereapp.core.design_system.ui

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models.PaletteModel

object MoodThemeData {
    val standardColors = listOf(
        Color(0xFF8CB342), // Отлично
        Color(0xFF00695C), // Хорошо
        Color(0xFFFBC02D), // Нормально
        Color(0xFFEF6C00), // Плохо
        Color(0xFFD50000)  // Ужасно
    )

    val balancedEmotions = listOf(
        Color(0xFF2E7D32), // спокойный зелёный (отлично)
        Color(0xFF1565C0), // синий (хорошо)
        Color(0xFF8E24AA), // фиолетовый (нормально)
        Color(0xFFEF6C00), // оранжевый (плохо)
        Color(0xFFC62828)  // красный (ужасно)
    )

    val coolContrast = listOf(
        Color(0xFF00695C), // teal
        Color(0xFF283593), // indigo
        Color(0xFF6A1B9A), // purple
        Color(0xFFAD1457), // pink-red
        Color(0xFF4E342E)  // dark brown
    )

    val urbanNeon = listOf(
        Color(0xFF00897B), // teal
        Color(0xFF3949AB), // blue
        Color(0xFF7B1FA2), // violet
        Color(0xFFEF5350), // red
        Color(0xFFFF6F00)  // amber
    )


    val palettes = listOf(
        PaletteModel(0, "Стандартная", standardColors),
        PaletteModel(1, "Палитра 1", balancedEmotions),
        PaletteModel(2, "Палитра 2", coolContrast),
        PaletteModel(3, "Палитра 3", urbanNeon),
        PaletteModel(4, "Палитра 4", standardColors)
    )

}

val availableMoodIcons = listOf(
    R.drawable.ic_mood_great, //
    R.drawable.ic_mood_neutral, //
    R.drawable.ic_mood_sad, //
    R.drawable.ic_mood_bad, //
    R.drawable.ic_mood_satisfied, //
    R.drawable.ic_mood_very_satisfied, //
    R.drawable.ic_mood_very_dissatisfied, //
    R.drawable.ic_mood_dissatisfied, //
    R.drawable.ic_mood_calm, //
    R.drawable.ic_mood_content, //
    R.drawable.ic_mood_excited, //
    R.drawable.ic_mood_extremely_dissatisfied, //
    R.drawable.ic_mood_frustrated, //
    R.drawable.ic_mood_sick, //
    R.drawable.ic_mood_stressed, //
    R.drawable.ic_mood_worried //
)