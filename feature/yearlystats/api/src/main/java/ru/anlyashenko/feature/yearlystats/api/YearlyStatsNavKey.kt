package ru.anlyashenko.feature.yearlystats.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.anlyashenko.core.navigation.Navigator

@Serializable
object YearlyStatsNavKey : NavKey

fun Navigator.navigateToYearlyStat() {
    navigate(YearlyStatsNavKey)
}

