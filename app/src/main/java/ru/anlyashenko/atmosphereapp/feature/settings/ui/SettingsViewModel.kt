package ru.anlyashenko.atmosphereapp.feature.settings.ui

import android.os.Build
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<SettingsEvent, SettingsState, SettingsEffect>() {

    override fun createInitialState(): SettingsState = SettingsState()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.notificationEnabledFlow,
                settingsRepository.notificationHourFlow,
                settingsRepository.notificationMinuteFlow
            ) { isEnabled, hour, minute ->
                currentState.copy(
                    isNotificationsEnabled = isEnabled,
                    notificationHour = hour,
                    notificationMinute = minute,
                    isLoading = false
                )
            }.collect { newState ->
                setState { newState }
            }
        }
    }

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackClick -> setEffect { SettingsEffect.NavigateBack }
            is SettingsEvent.OnAppearanceClick -> setEffect { SettingsEffect.NavigateToAppearance }
            is SettingsEvent.OnEditMoodsClick -> setEffect { SettingsEffect.NavigateToEditMoods }

            is SettingsEvent.OpenNotificationSheet -> setState { copy(showNotificationSheet = true) }
            is SettingsEvent.OpenLanguageDialog -> setState { copy(showLanguageDialog = true) }
            is SettingsEvent.DismissDialogs -> setState {
                copy(showNotificationSheet = false, showLanguageDialog = false)
            }

            is SettingsEvent.SaveNotificationSettings -> saveNotificationSettings(event)
            is SettingsEvent.OnPermissionResult -> handlePermissionResult(event.isGranted)
        }
    }


    private fun saveNotificationSettings(event: SettingsEvent.SaveNotificationSettings) {
        viewModelScope.launch {
            settingsRepository.saveNotificationSettings(event.isEnabled, event.hour, event.minute)
        }

        setState {
            copy(
                isNotificationsEnabled = event.isEnabled,
                notificationHour = event.hour,
                notificationMinute = event.minute,
                showNotificationSheet = false
            )
        }

        if (event.isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                setEffect { SettingsEffect.RequestNotificationPermission }
            } else {
                setEffect { SettingsEffect.ScheduleNotification(event.hour, event.minute) }
            }
        } else {
            setEffect { SettingsEffect.CancelNotification }
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            setEffect {
                SettingsEffect.ScheduleNotification(
                    currentState.notificationHour,
                    currentState.notificationMinute
                )
            }
        } else {
            setState { copy(isNotificationsEnabled = false) }
        }
    }

}