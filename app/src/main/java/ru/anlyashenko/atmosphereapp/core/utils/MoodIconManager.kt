package ru.anlyashenko.atmosphereapp.core.utils

import ru.anlyashenko.atmosphereapp.R

object MoodIconManager {
    private val icons = mapOf(
        "ic_mood_very_satisfied" to R.drawable.ic_mood_very_satisfied,
        "ic_mood_satisfied" to R.drawable.ic_mood_satisfied,
        "ic_mood_neutral" to R.drawable.ic_mood_neutral,
        "ic_mood_dissatisfied" to R.drawable.ic_mood_dissatisfied,
        "ic_mood_very_dissatisfied" to R.drawable.ic_mood_very_dissatisfied,
    )

    fun getIconRes(key: String): Int = icons[key] ?: R.drawable.ic_mood_neutral

    fun getAllIcons(): List<String> = icons.keys.toList()
}