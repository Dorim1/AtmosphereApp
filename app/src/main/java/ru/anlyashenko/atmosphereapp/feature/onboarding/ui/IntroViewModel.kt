package ru.anlyashenko.atmosphereapp.feature.onboarding.ui

// todo: ----
/*
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem
import ru.anlyashenko.atmosphereapp.domain.notification.AlarmScheduler
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler
) : BaseViewModel<IntroEvent, IntroState, IntroEffect>() {

    override fun createInitialState(): IntroState  = IntroState

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
                    hour = NotificationDefaults.DEFAULT_HOUR,
                    minute = NotificationDefaults.DEFAULT_MINUTE
                )

                alarmScheduler.schedule(
                    AlarmItem()
                )
            } else {
                settingsRepository.saveNotificationSettings(
                    isEnabled = false,
                    hour = NotificationDefaults.DEFAULT_HOUR,
                    minute = NotificationDefaults.DEFAULT_MINUTE
                )
            }

            settingsRepository.saveOnboardingCompleted(true)
            setEffect { IntroEffect.NavigateToHome }
        }
    }


}*/
