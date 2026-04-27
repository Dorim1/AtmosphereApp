package ru.anlyashenko.core.notifications

import ru.anlyashenko.core.data.repository.SettingsRepository

// todo: Internal?
internal data class AlarmItem (
    val id: Int = SettingsRepository.NOTIFICATION_ALARM_ID,
    val hour: Int = SettingsRepository.DEFAULT_NOTIFICATION_HOUR,
    val minute: Int = SettingsRepository.DEFAULT_NOTIFICATION_MINUTE,
)

internal interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(id: Int)
}