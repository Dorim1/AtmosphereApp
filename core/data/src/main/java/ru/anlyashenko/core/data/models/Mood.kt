package ru.anlyashenko.core.data.models

import ru.anlyashenko.core.model.Mood
import ru.anlyashenko.database.entity.MoodDBO

fun Mood.toEntity(): MoodDBO = MoodDBO(
    id = id,
    level = level,
    colorHex = colorHex,
    iconKey = iconKey,
    customName = customName
)