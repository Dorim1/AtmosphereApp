package ru.anlyashenko.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.anlyashenko.core.data.repository.SettingsRepository
import javax.inject.Inject

class AppNotifier @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun showMoodNotification(isMoodLogged: Boolean) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mood_reminder_channel"

        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val titlesArrayId = if (isMoodLogged) R.array.notification_logged_titles else R.array.notification_reminders_titles
        val textArrayId = if (isMoodLogged) R.array.notification_logged_texts else R.array.notification_reminders_texts

        val title = context.resources.getStringArray(titlesArrayId).random()
        val text = context.resources.getStringArray(textArrayId).random()

        val activityIntent =
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(SettingsRepository.NOTIFICATION_ALARM_ID, notification)
    }
}
