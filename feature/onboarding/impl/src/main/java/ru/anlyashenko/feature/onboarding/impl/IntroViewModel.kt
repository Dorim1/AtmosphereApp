package ru.anlyashenko.feature.onboarding.impl

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.notifications.AlarmItem
import ru.anlyashenko.core.notifications.AlarmScheduler
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler
) : BaseViewModel<IntroEvent, IntroState, IntroEffect>() {

    override fun createInitialState(): IntroState = IntroState

    override fun handleEvent(event: IntroEvent) {
        when (event) {
            is IntroEvent.CompleteOnboarding -> completeOnboarding(event.isNotificationGranted)
        }
    }

    private fun completeOnboarding(isNotificationGranted: Boolean) {
        viewModelScope.launch {
            if (isNotificationGranted) {

                settingsRepository.saveNotificationSettings(
                    isEnabled = true,
                    hour = SettingsRepository.DEFAULT_NOTIFICATION_HOUR,
                    minute = SettingsRepository.DEFAULT_NOTIFICATION_MINUTE
                )

                alarmScheduler.schedule(
                    AlarmItem()
                )
            } else {
                settingsRepository.saveNotificationSettings(
                    isEnabled = false,
                    hour = SettingsRepository.DEFAULT_NOTIFICATION_HOUR,
                    minute = SettingsRepository.DEFAULT_NOTIFICATION_MINUTE
                )
            }

            settingsRepository.saveOnboardingCompleted(true)
            setEffect { IntroEffect.NavigateToHome }
        }
    }


}