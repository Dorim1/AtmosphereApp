package ru.anlyashenko.feature.profile.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.profile.api.ProfileNavKey
import ru.anlyashenko.feature.profile.impl.ProfileScreen
import ru.anlyashenko.feature.settings.api.navigateToSettings
import ru.anlyashenko.feature.yearlystats.api.navigateToYearlyStat

fun EntryProviderScope<NavKey>.profileEntry(navigator: Navigator) {
    entry<ProfileNavKey> {
        ProfileScreen(
            onNavigateToSettings = navigator::navigateToSettings,
            onNavigateToYearlyStats = navigator::navigateToYearlyStat
        )
    }
}
