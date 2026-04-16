package ru.anlyashenko.atmosphereapp.domain.notification

import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(id: Int)
}