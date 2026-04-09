package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.profile.models.DailyMoodStat
import ru.anlyashenko.atmosphereapp.feature.profile.models.MoodCountItem
import ru.anlyashenko.atmosphereapp.feature.profile.ui.UiText.DynamicString
import ru.anlyashenko.atmosphereapp.feature.profile.ui.UiText.StringResource

data class ProfileState(
    val totalEntries: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val moodCounts: List<MoodCountItem> = emptyList(),
    val chartData: List<DailyMoodStat> = emptyList(),
    val chartInsight: UiText = UiText.StringResource(R.string.profile_not_enough_data),
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

// todo: вынести куда нибудь в utils
sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @param:StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is DynamicString -> value
        is StringResource -> {
            val resolvedArgs = args.map { arg ->
                if (arg is UiText) arg.asString() else arg
            }.toTypedArray()
            stringResource(resId, *resolvedArgs)
        }
    }
}