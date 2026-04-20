package ru.anlyashenko.atmosphereapp.domain.model

import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults

data class AlarmItem(
    val id: Int = NotificationDefaults.ALARM_ID,
    val hour: Int = NotificationDefaults.DEFAULT_HOUR,
    val minute: Int = NotificationDefaults.DEFAULT_MINUTE
)
