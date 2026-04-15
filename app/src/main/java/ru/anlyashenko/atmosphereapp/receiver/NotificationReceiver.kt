package ru.anlyashenko.atmosphereapp.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ru.anlyashenko.atmosphereapp.MainActivity
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.navigation.Destination

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mood_reminder_channel"

        val channel = NotificationChannel(
            channelId,
            "Уведомления",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Канал для ежедневных напоминаний о записи настроения"
        }
        notificationManager.createNotificationChannel(channel)

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            putExtra("destination", "HomeScreen")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_setting_notifications) // todo: поменять
            .setContentTitle("Как прошёл ваш день?")
            .setContentText("Уделите минуту, чтобы отметить своё настроение.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1001, notification)
    }


}