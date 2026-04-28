package ru.anlyashenko.feature.settings.appearance

import ru.anlyashenko.core.designsystem.ext.dpValue
import ru.anlyashenko.core.model.CornerRadiusMode
import ru.anlyashenko.core.model.ThemeMode
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState

data class AppearanceState(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val cornerRadius: CornerRadiusMode = CornerRadiusMode.BIG
) : UiState

sealed interface AppearanceEvent : UiEvent {
    data class OnThemeSelected(val theme: ThemeMode) : AppearanceEvent
    data class OnCornerRadiusSelected(val radius: CornerRadiusMode) : AppearanceEvent
    data object OnBackClick : AppearanceEvent
}

sealed interface AppearanceEffect : UiEffect {
    data object NavigateToBack : AppearanceEffect
}