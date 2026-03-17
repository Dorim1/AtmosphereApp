package ru.anlyashenko.atmosphereapp.data.model

import ru.anlyashenko.atmosphereapp.feature.home.ui.DayState

data class DayUI(
    val number: Int,
    val month: String,
    val state: DayState
)
