package ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState

data class AppearanceState(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val cornerRadius: CornerRadiusMode = CornerRadiusMode.MODERATE
) : UiState

sealed interface AppearanceEvent : UiEvent {
    data class OnThemeSelected(val theme: ThemeMode) : AppearanceEvent
    data class OnCornerRadiusSelected(val radius: CornerRadiusMode) : AppearanceEvent
    data object OnBackClick : AppearanceEvent
}

sealed interface AppearanceEffect : UiEffect {
    data object NavigateToBack : AppearanceEffect
}