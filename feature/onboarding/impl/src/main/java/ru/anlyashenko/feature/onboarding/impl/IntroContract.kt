package ru.anlyashenko.feature.onboarding.impl

import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState

data object IntroState : UiState

sealed interface IntroEvent : UiEvent {
    data class CompleteOnboarding(val isNotificationGranted: Boolean) : IntroEvent
}

sealed interface IntroEffect : UiEffect {
    object NavigateToHome : IntroEffect
}
