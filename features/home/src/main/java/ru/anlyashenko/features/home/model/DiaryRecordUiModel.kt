package ru.anlyashenko.features.home.model

import java.time.LocalDate

data class DiaryRecordUiModel(
    val date: LocalDate,
    val mood: MoodUiModel?,
    val note: String?
) {
    val hasMood: Boolean get() = mood != null
    val hasNote: Boolean get() = note.isNullOrBlank()
}
