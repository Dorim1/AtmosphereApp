package ru.anlyashenko.atmosphereapp.feature.home.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel


data class HomeState(
    val weather: WeatherUiModel? = null,
    val isLoadingWeather: Boolean = false,

    val weekRecords: List<DailyRecord> = emptyList(),

    val currentDayRecord: DailyRecord? = null,
    val isRecordLoading: Boolean = false,

    val showMoodSheet: Boolean = false,
    val showNoteDialog: Boolean = false,

): UiState

sealed interface HomeEvent : UiEvent {
    object LoadWeather : HomeEvent

    object OnMoodButtonClick : HomeEvent
    object OnNoteButtonClick : HomeEvent
    object DismissDialogs : HomeEvent

    data class OnMoodSelected(val moodId: Int) : HomeEvent
    data class OnSaveNote(val noteText: String) : HomeEvent

}

sealed interface HomeEffect : UiEffect {
    data class ShowSnackbar(val message: String) : HomeEffect
}
