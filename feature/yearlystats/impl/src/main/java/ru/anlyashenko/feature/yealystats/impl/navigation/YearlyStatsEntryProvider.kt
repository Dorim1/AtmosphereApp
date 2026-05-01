package ru.anlyashenko.feature.yealystats.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.yealystats.impl.YearlyStatsScreen
import ru.anlyashenko.feature.yearlystats.api.YearlyStatsNavKey

fun EntryProviderScope<NavKey>.yearlyStatsEntry(navigator: Navigator) {
    entry<YearlyStatsNavKey> {
        YearlyStatsScreen(
            onNavigateBack = { navigator.goBack() }
        )
    }
}