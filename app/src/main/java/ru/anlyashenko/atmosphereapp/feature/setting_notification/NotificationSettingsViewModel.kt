package ru.anlyashenko.atmosphereapp.feature.setting_notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem
import ru.anlyashenko.atmosphereapp.domain.notification.AlarmScheduler
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEffect
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEvent
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationPermissionManager
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler,
    private val permissionManager: NotificationPermissionManager
): BaseViewModel<NotificationSettingsEvent, NotificationSettingsState, NotificationSettingsEffect>() {

    override fun createInitialState(): NotificationSettingsState = NotificationSettingsState()

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

    override fun handleEvent(event: NotificationSettingsEvent) {
        when (event) {
            is NotificationSettingsEvent.OnBackClick -> setEffect { NotificationSettingsEffect.NavigateBack }
            is NotificationSettingsEvent.OnShouldOpenSettings -> setEffect { NotificationSettingsEffect.OpenAppSettings }
            is NotificationSettingsEvent.SaveNotificationSettings -> saveNotificationSettings(event)
            is NotificationSettingsEvent.OnPermissionResult -> handlePermissionResult(event.isGranted)
        }
    }

    private fun saveNotificationSettings(event: NotificationSettingsEvent.SaveNotificationSettings) {
        viewModelScope.launch {
            settingsRepository.saveNotificationSettings(event.isEnabled, event.hour, event.minute)

            setState {
                copy(
                    notificationHour = event.hour,
                    notificationMinute = event.minute,
                )
            }

            if (event.isEnabled) {
                when  {
                    permissionManager.checkPermission() -> {
                        alarmScheduler.schedule(AlarmItem(hour = event.hour, minute = event.minute))
                    }
                    else -> {
                        setEffect { NotificationSettingsEffect.RequestNotificationPermission }
                    }
                }
            } else {
                alarmScheduler.cancel(NotificationDefaults.ALARM_ID)
            }

            setEffect { NotificationSettingsEffect.NavigateBack }
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            if (currentState.isNotificationsEnabled) {
                alarmScheduler.schedule(
                    AlarmItem(
                        hour = currentState.notificationHour,
                        minute = currentState.notificationMinute
                    )
                )
            }
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