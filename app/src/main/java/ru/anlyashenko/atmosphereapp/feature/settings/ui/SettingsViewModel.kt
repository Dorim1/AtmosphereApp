package ru.anlyashenko.atmosphereapp.feature.settings.ui

import android.os.Build
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem
import ru.anlyashenko.atmosphereapp.domain.notification.AlarmScheduler
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationPermissionManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler,
    private val permissionManager: NotificationPermissionManager
) : BaseViewModel<SettingsEvent, SettingsState, SettingsEffect>() {

    override fun createInitialState(): SettingsState = SettingsState()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.notificationEnabledFlow,
                settingsRepository.notificationHourFlow,
                settingsRepository.notificationMinuteFlow,
                settingsRepository.themeModeFlow
            ) { isEnabled, hour, minute, theme ->
                currentState.copy(
                    isNotificationsEnabled = isEnabled,
                    notificationHour = hour,
                    notificationMinute = minute,
                    theme = theme,
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
            is SettingsEvent.OnShouldOpenSettings -> setEffect { SettingsEffect.OpenAppSettings }

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
            when  {
                permissionManager.checkPermission() -> {
                    alarmScheduler.schedule(AlarmItem(hour = event.hour, minute = event.minute))
                }
                else -> {
                    setEffect { SettingsEffect.RequestNotificationPermission }
                }
            }
        }

    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            alarmScheduler.schedule(
                AlarmItem(
                    hour = currentState.notificationHour,
                    minute = currentState.notificationMinute
                )
            )
        } else {
            viewModelScope.launch {
                settingsRepository.saveNotificationSettings(
                    isEnabled = false,
                    hour = currentState.notificationHour,
                    minute = currentState.notificationMinute
                )
            }
            setState { copy(isNotificationsEnabled = false) }
        }
    }

}