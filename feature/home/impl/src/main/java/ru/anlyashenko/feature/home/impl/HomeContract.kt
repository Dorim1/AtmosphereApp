package ru.anlyashenko.feature.home.impl

import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState
import ru.anlyashenko.feature.home.impl.model.DiaryRecordUiModel
import ru.anlyashenko.feature.home.impl.model.MoodUiModel
import ru.anlyashenko.feature.home.impl.model.WeatherUiModel

data class HomeState(
    val weather: WeatherUiModel? = null,
    val isLoadingWeather: Boolean = false,

    val weekRecords: List<DiaryRecordUiModel> = emptyList(),
    val availableMoods: List<MoodUiModel> = emptyList(),

    val isRecordLoading: Boolean = false,

    val showMoodSheet: Boolean = false,
    val showNoteDialog: Boolean = false,

    ) : UiState

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
