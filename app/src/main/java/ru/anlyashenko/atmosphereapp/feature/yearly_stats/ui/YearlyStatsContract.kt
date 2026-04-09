package ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel

data class YearlyStatsState(
    val records: List<DiaryRecordUiModel> = emptyList(),
    val availableMoods: List<MoodUiModel> = emptyList(),
) : UiState

sealed class YearlyStatsEvent : UiEvent {
    object OnBackClick : YearlyStatsEvent()
}

sealed class YearlyStatsEffect : UiEffect {
    object NavigateBack : YearlyStatsEffect()
}