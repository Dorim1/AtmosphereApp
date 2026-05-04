package ru.anlyashenko.feature.yealystats.impl

import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState
import ru.anlyashenko.feature.yealystats.impl.model.YearlyMoodUiModel
import ru.anlyashenko.feature.yealystats.impl.model.YearlyRecordUiModel

data class YearlyStatsState(
    val records: List<YearlyRecordUiModel> = emptyList(),
    val availableMoods: List<YearlyMoodUiModel> = emptyList(),
    val hasEnoughMoodData: Boolean = false,
) : UiState

sealed class YearlyStatsEvent : UiEvent {
    object OnBackClick : YearlyStatsEvent()
}

sealed class YearlyStatsEffect : UiEffect {
    object NavigateBack : YearlyStatsEffect()
}