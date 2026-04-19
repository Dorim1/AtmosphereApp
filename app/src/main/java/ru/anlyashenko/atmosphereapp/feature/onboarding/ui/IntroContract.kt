package ru.anlyashenko.atmosphereapp.feature.onboarding.ui

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState

data object IntroState: UiState

sealed interface IntroEvent : UiEvent {
    object CompleteOnboarding: IntroEvent
}

sealed interface IntroEffect : UiEffect {
    object NavigateToHome : IntroEffect
}