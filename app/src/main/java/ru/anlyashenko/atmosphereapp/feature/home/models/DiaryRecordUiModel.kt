package ru.anlyashenko.atmosphereapp.feature.home.models

import java.time.LocalDate

// todo: ----
data class DiaryRecordUiModel(
    val date: LocalDate,
    val mood: MoodUiModel? = null,
    val note: String? = null
) {
    val hasMood: Boolean get() = mood != null
    val hasNote: Boolean get() = note != null
}
