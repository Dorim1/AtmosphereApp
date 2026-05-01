package ru.anlyashenko.feature.settings.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.settings.api.AppearanceNavKey
import ru.anlyashenko.feature.settings.api.EditMoodsNavKey
import ru.anlyashenko.feature.settings.api.NotificationSettingsNavKey
import ru.anlyashenko.feature.settings.api.SettingsNavKey
import ru.anlyashenko.feature.settings.impl.appearance.AppearanceScreen
import ru.anlyashenko.feature.settings.impl.main.SettingsScreen
import ru.anlyashenko.feature.settings.impl.moods.EditMoodsScreen
import ru.anlyashenko.feature.settings.impl.notifications.NotificationSettingsRoute

fun EntryProviderScope<NavKey>.settingsEntry(navigator: Navigator) {
    entry<SettingsNavKey> {
        SettingsScreen(
            onBackClick = { navigator.goBack() },
            onNavigateToAppearance = { navigator.navigate(AppearanceNavKey) },
            onNavigateToEditMoods = { navigator.navigate(EditMoodsNavKey) },
            onNavigateToNotificationSettings = { navigator.navigate(NotificationSettingsNavKey) }
        )
    }
}

fun EntryProviderScope<NavKey>.appearanceEntry(navigator: Navigator) {
    entry<AppearanceNavKey> {
        AppearanceScreen(
            onBackClick = { navigator.goBack() }
        )
    }
}

fun EntryProviderScope<NavKey>.editMoodsEntry(navigator: Navigator) {
    entry<EditMoodsNavKey> {
        EditMoodsScreen(
            onBackClick = { navigator.goBack() }
        )
    }
}

fun EntryProviderScope<NavKey>.notificationSettingsEntry(navigator: Navigator) {
    entry<NotificationSettingsNavKey> {
        NotificationSettingsRoute(
            onBackClick = { navigator.goBack() }
        )
    }
}