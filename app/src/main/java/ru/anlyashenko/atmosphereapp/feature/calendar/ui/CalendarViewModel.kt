package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : BaseViewModel<CalendarEvent, CalendarState, CalendarEffect>() {

    init {
        observeAllRecords()
    }

    override fun createInitialState(): CalendarState = CalendarState()
    private fun observeAllRecords() {
        viewModelScope.launch {
            diaryRepository.getAllRecordsFlow().collect { allRecords ->
                setState { copy(records = allRecords) }
            }
        }
    }



    override fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnDateSelected -> {
                setState { copy(selectedDate = event.date) }
            }

            is CalendarEvent.OnDeleteNote -> {
                viewModelScope.launch {
                    val dateToDelete = currentState.selectedDate
                    diaryRepository.saveNote(dateToDelete, "")
                }
            }
        }
    }
}