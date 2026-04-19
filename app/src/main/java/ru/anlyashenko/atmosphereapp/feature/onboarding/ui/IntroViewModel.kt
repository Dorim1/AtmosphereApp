package ru.anlyashenko.atmosphereapp.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<IntroEvent, IntroState, IntroEffect>() {

    override fun createInitialState(): IntroState  = IntroState

    override fun handleEvent(event: IntroEvent) {
        when (event) {
            IntroEvent.CompleteOnboarding -> completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            settingsRepository.saveOnboardingCompleted(true)
            setEffect { IntroEffect.NavigateToHome }
        }
    }


}