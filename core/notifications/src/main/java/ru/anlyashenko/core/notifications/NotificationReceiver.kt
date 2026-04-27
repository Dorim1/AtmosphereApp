package ru.anlyashenko.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.core.data.repository.SettingsRepository
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver: BroadcastReceiver() {

    @Inject
    lateinit var appNotifier: AppNotifier

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent) {
        appNotifier.showMoodNotification()

        val hour = intent.getIntExtra("EXTRA_HOUR", SettingsRepository.DEFAULT_NOTIFICATION_HOUR)
        val minute = intent.getIntExtra("EXTRA_MINUTE", SettingsRepository.DEFAULT_NOTIFICATION_MINUTE)
        val id = intent.getIntExtra("EXTRA_ID", SettingsRepository.NOTIFICATION_ALARM_ID)

        alarmScheduler.schedule(AlarmItem(id, hour, minute))
    }

}