package ru.anlyashenko.atmosphereapp.feature.home.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.domain.model.WeatherUiModel


data class HomeState(
    // Данные для погоды
    val weather: WeatherUiModel? = null,
    val isLoadingWeather: Boolean = false,

    // Данные текущего дня
    val currentDayRecord: DailyRecord? = null,
    val isRecordLoading: Boolean = false,

    // Состояние окон
    val showMoodSheet: Boolean = false,
    val showNoteDialog: Boolean = false,
): UiState

sealed class HomeEvent : UiEvent {
    data class LoadWeather(val lat: Double, val lon: Double) : HomeEvent()

    object OnMoodButtonClick : HomeEvent()

    data class OnMoodSelected(val moodId: Int) : HomeEvent()

    object OnNoteButtonClick : HomeEvent()

    data class OnSaveNote(val noteText: String) : HomeEvent()

    object DismissDialogs : HomeEvent()
}

sealed class HomeEffect : UiEffect {
    data class ShowSnackbar(val message: String) : HomeEffect()
    object NavigationToSettings : HomeEffect()
}
