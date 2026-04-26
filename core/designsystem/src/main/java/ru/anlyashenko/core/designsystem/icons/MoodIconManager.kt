package ru.anlyashenko.core.designsystem.icons

import ru.anlyashenko.core.designsystem.R

object MoodIconManager {

    const val ICON_VERY_SATISFIED = "ic_mood_very_satisfied"
    const val ICON_SATISFIED = "ic_mood_satisfied"
    const val ICON_NEUTRAL = "ic_mood_neutral"
    const val ICON_DISSATISFIED = "ic_mood_dissatisfied"
    const val ICON_VERY_DISSATISFIED = "ic_mood_very_dissatisfied"
    const val ICON_GREAT = "ic_mood_great"
    const val ICON_SAD = "ic_mood_sad"
    const val ICON_BAD = "ic_mood_bad"
    const val ICON_CALM = "ic_mood_calm"
    const val ICON_CONTENT = "ic_mood_content"
    const val ICON_EXCITED = "ic_mood_excited"
    const val ICON_EXTREMELY_DISSATISFIED = "ic_mood_extremely_dissatisfied"
    const val ICON_FRUSTRATED = "ic_mood_frustrated"
    const val ICON_SICK = "ic_mood_sick"
    const val ICON_STRESSED = "ic_mood_stressed"
    const val ICON_WORRIED = "ic_mood_worried"

    private val icons = mapOf(
        ICON_VERY_SATISFIED to R.drawable.ic_mood_very_satisfied,
        ICON_SATISFIED to R.drawable.ic_mood_satisfied,
        ICON_NEUTRAL to R.drawable.ic_mood_neutral,
        ICON_DISSATISFIED to R.drawable.ic_mood_dissatisfied,
        ICON_VERY_DISSATISFIED to R.drawable.ic_mood_very_dissatisfied,
        ICON_GREAT to R.drawable.ic_mood_great,
        ICON_SAD to R.drawable.ic_mood_sad,
        ICON_BAD to R.drawable.ic_mood_bad,
        ICON_CALM to R.drawable.ic_mood_calm,
        ICON_CONTENT to R.drawable.ic_mood_content,
        ICON_EXCITED to R.drawable.ic_mood_excited,
        ICON_EXTREMELY_DISSATISFIED to R.drawable.ic_mood_extremely_dissatisfied,
        ICON_FRUSTRATED to R.drawable.ic_mood_frustrated,
        ICON_SICK to R.drawable.ic_mood_sick,
        ICON_STRESSED to R.drawable.ic_mood_stressed,
        ICON_WORRIED to R.drawable.ic_mood_worried
    )

//    val allIconKeys: List<String> = icons.keys.toList()
//    val allIconRes: List<Int> = icons.values.toList()

    fun getIconRes(key: String): Int = icons[key] ?: R.drawable.ic_mood_neutral

    fun getKeyByRes(resId: Int): String {
        return icons.entries.find { it.value == resId }?.key
            ?: "ic_mood_neutral"
    }

    fun getAllIcons(): List<String> = icons.keys.toList()
    fun getAllIconRes(): List<Int> = icons.values.toList()
}