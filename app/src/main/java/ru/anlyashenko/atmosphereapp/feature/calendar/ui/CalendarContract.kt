package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import java.time.LocalDate

data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val records: List<DiaryRecordUiModel> = emptyList()
) : UiState {
    val moodMap: Map<LocalDate, Color>
        get() = records
            .filter { it.mood != null }
            .associate { it.date to it.mood!!.color }

    val daysWithNotes: Set<LocalDate>
        get() = records
            .filter { !it.note.isNullOrBlank() }
            .map { it.date }
            .toSet()

    val selectedRecord: DiaryRecordUiModel?
        get() = records.find { it.date == selectedDate }
}

sealed class CalendarEvent : UiEvent {
    data class OnDateSelected(val date: LocalDate) : CalendarEvent()
    object OnDeleteNote : CalendarEvent()
}

sealed class CalendarEffect : UiEffect {

}