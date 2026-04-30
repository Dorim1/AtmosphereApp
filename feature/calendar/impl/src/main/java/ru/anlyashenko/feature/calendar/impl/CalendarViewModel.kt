package ru.anlyashenko.feature.calendar.impl

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import ru.anlyashenko.feature.calendar.impl.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<CalendarEvent, CalendarState, CalendarEffect>() {

    init {
        observeAllRecords()
    }

    override fun createInitialState(): CalendarState = CalendarState()
    private fun observeAllRecords() {
        /*viewModelScope.launch {
            diaryRepository.getAllRecordsFlow().collect { allRecords ->
                setState { copy(records = allRecords) }
            }
        }*/

        viewModelScope.launch {
            combine(
                diaryRepository.getAllRecordsFlow(),
                settingsRepository.selectedPaletteFlow
            ) { domainRecords, paletteId ->

                val activePalette = MoodPalettes.allPalettes.find { it.id == paletteId }
                    ?: MoodPalettes.allPalettes.first()

                domainRecords.map { record ->
                    record.toUiModel(activePalette)
                }
            }.collect { calendarRecords ->
                setState { copy(records = calendarRecords) }
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