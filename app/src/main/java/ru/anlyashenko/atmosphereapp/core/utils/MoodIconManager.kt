package ru.anlyashenko.atmosphereapp.core.utils

import ru.anlyashenko.atmosphereapp.R

object MoodIconManager {

    private val icons = mapOf(
        "ic_mood_very_satisfied" to R.drawable.ic_mood_very_satisfied,
        "ic_mood_satisfied" to R.drawable.ic_mood_satisfied,
        "ic_mood_neutral" to R.drawable.ic_mood_neutral,
        "ic_mood_dissatisfied" to R.drawable.ic_mood_dissatisfied,
        "ic_mood_very_dissatisfied" to R.drawable.ic_mood_very_dissatisfied,
        "ic_mood_great" to R.drawable.ic_mood_great,
        "ic_mood_sad" to R.drawable.ic_mood_sad,
        "ic_mood_bad" to R.drawable.ic_mood_bad,
        "ic_mood_calm" to R.drawable.ic_mood_calm,
        "ic_mood_content" to R.drawable.ic_mood_content,
        "ic_mood_excited" to R.drawable.ic_mood_excited,
        "ic_mood_extremely_dissatisfied" to R.drawable.ic_mood_extremely_dissatisfied,
        "ic_mood_frustrated" to R.drawable.ic_mood_frustrated,
        "ic_mood_sick" to R.drawable.ic_mood_sick,
        "ic_mood_stressed" to R.drawable.ic_mood_stressed,
        "ic_mood_worried" to R.drawable.ic_mood_worried
    )

    fun getIconRes(key: String): Int = icons[key] ?: R.drawable.ic_mood_neutral

    fun getKeyByRes(resId: Int): String {
        return icons.entries.find { it.value == resId }?.key
            ?: "ic_mood_neutral"
    }

    fun getAllIcons(): List<String> = icons.keys.toList()
}