package ru.anlyashenko.atmosphereapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem
import ru.anlyashenko.atmosphereapp.domain.notification.AlarmScheduler
import ru.anlyashenko.atmosphereapp.receiver.notification.AppNotifier
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver: BroadcastReceiver() {

    @Inject
    lateinit var appNotifier: AppNotifier

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent) {
        appNotifier.showMoodNotification()

        val hour = intent.getIntExtra("EXTRA_HOUR", NotificationDefaults.DEFAULT_HOUR)
        val minute = intent.getIntExtra("EXTRA_MINUTE", NotificationDefaults.DEFAULT_MINUTE)
        val id = intent.getIntExtra("EXTRA_ID", NotificationDefaults.ALARM_ID)

        alarmScheduler.schedule(AlarmItem(id, hour, minute))
    }

}