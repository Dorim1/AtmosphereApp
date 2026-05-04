package ru.anlyashenko.core.model

data class Mood(
    val id: Int,
    val level: Int,
    val colorHex: String,
    val iconKey: String,
    val customName: String? = null
)
