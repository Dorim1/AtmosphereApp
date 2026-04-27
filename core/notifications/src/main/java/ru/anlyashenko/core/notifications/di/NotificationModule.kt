package ru.anlyashenko.core.notifications.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.core.notifications.AlarmScheduler
import ru.anlyashenko.core.notifications.NotificationAlarmScheduler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    @Singleton
    fun bindAlarmScheduler(
        alarmSchedulerImpl: NotificationAlarmScheduler
    ): AlarmScheduler

}