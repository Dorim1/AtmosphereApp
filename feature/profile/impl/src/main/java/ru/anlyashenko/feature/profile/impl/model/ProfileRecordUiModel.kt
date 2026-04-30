package ru.anlyashenko.feature.profile.impl.model

import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.model.DiaryRecord
import java.time.LocalDate

data class ProfileRecordUiModel(
    val date: LocalDate,
    val mood: ProfileMoodUiModel?
) {
    val hasMood: Boolean get() = mood != null
}

fun DiaryRecord.toUiModel(activePalette: PaletteModel): ProfileRecordUiModel {
    return ProfileRecordUiModel(
        date = this.date,
        mood = this.mood?.toUiModel(activePalette)
    )
}