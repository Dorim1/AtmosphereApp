package ru.anlyashenko.feature.profile.impl

import androidx.compose.ui.graphics.Color
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState
import ru.anlyashenko.feature.profile.impl.model.DailyMoodStat
import ru.anlyashenko.feature.profile.impl.model.MoodCountItem

data class ProfileState(
    val totalEntries: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val moodCounts: List<MoodCountItem> = emptyList(),
    val chartData: List<DailyMoodStat> = emptyList(),
    val yAxisColors: List<Color> = emptyList(),
    val chartInsight: UiText = UiText.StringResource(R.string.profile_not_enough_data),
    val hasEnoughMoodData: Boolean = false,
    val yearlyProgress: Int = 0,
) : UiState

sealed interface ProfileEvent : UiEvent {
    object OnYearlyStatsClick : ProfileEvent
    object OnSettingsClick : ProfileEvent
}

sealed interface ProfileEffect : UiEffect {
    object NavigateToYearlyStats : ProfileEffect
    object NavigateToSettings : ProfileEffect
}