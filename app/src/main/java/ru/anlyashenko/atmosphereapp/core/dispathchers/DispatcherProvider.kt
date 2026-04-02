package ru.anlyashenko.atmosphereapp.core.dispathchers

import kotlinx.coroutines.CoroutineDispatcher

// TODO: Удалить
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}