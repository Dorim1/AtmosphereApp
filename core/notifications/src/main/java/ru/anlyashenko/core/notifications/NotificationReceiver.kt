package ru.anlyashenko.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appNotifier: AppNotifier

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var diaryRepository: DiaryRepository

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val today = LocalDate.now()
                val isMoodLoggedToday = diaryRepository.hasMoodForDate(today)

                withContext(Dispatchers.Main) {
                    appNotifier.showMoodNotification(isMoodLogged = isMoodLoggedToday)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    appNotifier.showMoodNotification(isMoodLogged = false)
                }
            } finally {
                val hour =
                    intent.getIntExtra("EXTRA_HOUR", SettingsRepository.DEFAULT_NOTIFICATION_HOUR)
                val minute =
                    intent.getIntExtra(
                        "EXTRA_MINUTE",
                        SettingsRepository.DEFAULT_NOTIFICATION_MINUTE
                    )
                val id = intent.getIntExtra("EXTRA_ID", SettingsRepository.NOTIFICATION_ALARM_ID)

                alarmScheduler.schedule(AlarmItem(id, hour, minute))

                pendingResult.finish()
            }
        }
    }
}
