package ru.anlyashenko.atmosphereapp.feature.profile.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.profile.models.DailyMoodStat
import ru.anlyashenko.atmosphereapp.feature.profile.models.MoodCountItem

data class ProfileState(
    val totalEntries: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val moodCounts: List<MoodCountItem> = emptyList(),
    val chartData: List<DailyMoodStat> = emptyList(),
    val chartInsight: String = "Недостаточно данных",
    val yearlyProgress: Int = 0,
) : UiState

sealed class ProfileEvent : UiEvent {
    object OnYearlyStatsClick : ProfileEvent()
    object OnSettingsClick : ProfileEvent()
}

sealed class ProfileEffect : UiEffect {
    object NavigateToYearlyStats : ProfileEffect()
    object NavigateToSettings : ProfileEffect()
}