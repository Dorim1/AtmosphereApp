package ru.anlyashenko.feature.settings.impl.notifications

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.notifications.AlarmItem
import ru.anlyashenko.core.notifications.AlarmScheduler
import ru.anlyashenko.core.notifications.NotificationPermissionManager
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler,
    private val permissionManager: NotificationPermissionManager
) : BaseViewModel<NotificationSettingsEvent, NotificationSettingsState, NotificationSettingsEffect>() {

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
            is NotificationSettingsEvent.OnResumePermissionCheck -> handleResumeCheck(event.isGranted)
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
                if (permissionManager.checkPermission()) {
                    alarmScheduler.schedule(
                        AlarmItem(
                            SettingsRepository.NOTIFICATION_ALARM_ID,
                            event.hour,
                            event.minute
                        )
                    )
                    setEffect { NotificationSettingsEffect.NavigateBack }
                } else {
                    setEffect { NotificationSettingsEffect.RequestNotificationPermission }
                }
            } else {
                alarmScheduler.cancel(SettingsRepository.NOTIFICATION_ALARM_ID)
                setEffect { NotificationSettingsEffect.NavigateBack }
            }
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            if (currentState.isNotificationsEnabled) {
                alarmScheduler.schedule(
                    AlarmItem(
                        id = SettingsRepository.NOTIFICATION_ALARM_ID,
                        hour = currentState.notificationHour,
                        minute = currentState.notificationMinute
                    )
                )
            }
            setEffect { NotificationSettingsEffect.NavigateBack }
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

    private fun handleResumeCheck(isGranted: Boolean) {
        if (isGranted) {
            return
        }

        if (currentState.isNotificationsEnabled) {
            viewModelScope.launch {
                settingsRepository.saveNotificationSettings(
                    isEnabled = false,
                    hour = currentState.notificationHour,
                    minute = currentState.notificationMinute
                )
            }
            setState { copy(isNotificationsEnabled = false) }
            alarmScheduler.cancel(SettingsRepository.NOTIFICATION_ALARM_ID)
        }
    }


}