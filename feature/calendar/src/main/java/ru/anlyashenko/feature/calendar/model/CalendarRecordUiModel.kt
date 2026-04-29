package ru.anlyashenko.feature.calendar.model

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.model.DiaryRecord
import java.time.LocalDate
import androidx.core.graphics.toColorInt

data class CalendarRecordUiModel(
    val date: LocalDate,
    val moodColor: Color?,
    val note: String?
)

fun DiaryRecord.toUiModel(activePalette: PaletteModel) : CalendarRecordUiModel {
    val color = this.mood?.let { domainMood ->
        val baseColor = Color(domainMood.colorHex.toColorInt())
        val colorIndex = activePalette.colors.size - domainMood.level
        activePalette.colors.getOrElse(colorIndex) { baseColor }
    }

    return CalendarRecordUiModel(
        date = this.date,
        moodColor = color,
        note = this.note
    )
}