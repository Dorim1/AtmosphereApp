package ru.anlyashenko.core.model

import java.time.LocalDate

data class DiaryEntry(
    val date: LocalDate,
    val moodId: Int? = null,
    val note: String? = null,
)