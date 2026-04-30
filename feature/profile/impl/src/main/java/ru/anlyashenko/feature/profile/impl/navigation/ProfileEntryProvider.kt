package ru.anlyashenko.feature.profile.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.profile.api.ProfileNavKey
import ru.anlyashenko.feature.profile.impl.ProfileRoute

fun EntryProviderScope<NavKey>.profileEntry(navigator: Navigator) {
    entry<ProfileNavKey> {
        ProfileRoute(
            onNavigateToSettings = {
                // TODO: Not implemented 
            },
            onNavigateToYearlyStats = {
                // TODO: Not implemented 
            }
        )
    }
}