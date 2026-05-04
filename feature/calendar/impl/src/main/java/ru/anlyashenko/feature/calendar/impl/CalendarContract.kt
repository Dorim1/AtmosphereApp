package ru.anlyashenko.feature.calendar.impl

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState
import ru.anlyashenko.feature.calendar.impl.model.CalendarRecordUiModel
import java.time.LocalDate

data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val records: List<CalendarRecordUiModel> = emptyList()
) : UiState {
    val moodMap: Map<LocalDate, Color>
        get() = records
            .filter { it.moodColor != null }
            .associate { it.date to it.moodColor!! }

    val daysWithNotes: Set<LocalDate>
        get() = records
            .filter { !it.note.isNullOrBlank() }
            .map { it.date }
            .toSet()

    val selectedRecord: CalendarRecordUiModel?
        get() = records.find { it.date == selectedDate }
}

sealed class CalendarEvent : UiEvent {
    data class OnDateSelected(val date: LocalDate) : CalendarEvent()
    object OnDeleteNote : CalendarEvent()
}

sealed class CalendarEffect : UiEffect {

}