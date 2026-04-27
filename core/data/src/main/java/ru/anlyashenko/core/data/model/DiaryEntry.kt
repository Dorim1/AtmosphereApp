package ru.anlyashenko.core.data.model

import ru.anlyashenko.core.model.DiaryEntry
import ru.anlyashenko.database.entity.DiaryEntryDBO

fun DiaryEntry.toEntity(): DiaryEntryDBO = DiaryEntryDBO(
    date = date,
    moodId = moodId,
    note = note,
)
