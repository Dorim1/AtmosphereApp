package ru.anlyashenko.core.designsystem.icons

import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.model.MoodDefaults

object MoodIconManager {
    private val icons = mapOf(
        MoodDefaults.ICON_VERY_SATISFIED to R.drawable.ic_mood_very_satisfied,
        MoodDefaults.ICON_SATISFIED to R.drawable.ic_mood_satisfied,
        MoodDefaults.ICON_NEUTRAL to R.drawable.ic_mood_neutral,
        MoodDefaults.ICON_DISSATISFIED to R.drawable.ic_mood_dissatisfied,
        MoodDefaults.ICON_VERY_DISSATISFIED to R.drawable.ic_mood_very_dissatisfied,
        MoodDefaults.ICON_GREAT to R.drawable.ic_mood_great,
        MoodDefaults.ICON_SAD to R.drawable.ic_mood_sad,
        MoodDefaults.ICON_BAD to R.drawable.ic_mood_bad,
        MoodDefaults.ICON_CALM to R.drawable.ic_mood_calm,
        MoodDefaults.ICON_CONTENT to R.drawable.ic_mood_content,
        MoodDefaults.ICON_EXCITED to R.drawable.ic_mood_excited,
        MoodDefaults.ICON_EXTREMELY_DISSATISFIED to R.drawable.ic_mood_extremely_dissatisfied,
        MoodDefaults.ICON_FRUSTRATED to R.drawable.ic_mood_frustrated,
        MoodDefaults.ICON_SICK to R.drawable.ic_mood_sick,
        MoodDefaults.ICON_STRESSED to R.drawable.ic_mood_stressed,
        MoodDefaults.ICON_WORRIED to R.drawable.ic_mood_worried
    )


    fun getIconRes(key: String): Int = icons[key] ?: R.drawable.ic_mood_neutral

    fun getKeyByRes(resId: Int): String {
        return icons.entries.find { it.value == resId }?.key
            ?: MoodDefaults.ICON_NEUTRAL
    }

    fun getAllIcons(): List<String> = icons.keys.toList()
    fun getAllIconRes(): List<Int> = icons.values.toList()
}