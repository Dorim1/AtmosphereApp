package ru.anlyashenko.atmosphereapp.receiver.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.anlyashenko.atmosphereapp.MainActivity
import ru.anlyashenko.atmosphereapp.R
import javax.inject.Inject

class AppNotifier @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun showMoodNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mood_reminder_channel"

        val channel = NotificationChannel(
            channelId,
            "Уведомления",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_setting_notifications) // todo: Поменять
            .setContentTitle("Как прошёл ваш день?")
            .setContentText("Уделите минуту, чтобы отметить своё настроение.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1001, notification)
    }
}