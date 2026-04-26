package ru.anlyashenko.core.model

import java.time.LocalDate

data class DiaryRecord(
    val date: LocalDate,
    val mood: Mood?,
    val note: String?
) {
    /*val hasMood: Boolean get() = mood != null
    val hasNote: Boolean get() = note != null*/
}