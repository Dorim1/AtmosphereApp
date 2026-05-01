package ru.anlyashenko.feature.yealystats.impl.model

import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.model.DiaryRecord
import java.time.LocalDate

data class YearlyRecordUiModel(
    val date: LocalDate,
    val mood: YearlyMoodUiModel?,
    val hasNote: Boolean
) {
    val hasMood: Boolean get() = mood != null
}

fun DiaryRecord.toUiModel(activePalette: PaletteModel): YearlyRecordUiModel {
    return YearlyRecordUiModel(
        date = this.date,
        mood = this.mood?.toUiModel(activePalette),
        hasNote = !this.note.isNullOrBlank()
    )
}