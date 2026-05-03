package ru.anlyashenko.feature.calendar.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.calendar.api.CalendarNavKey
import ru.anlyashenko.feature.calendar.impl.CalendarScreen

fun EntryProviderScope<NavKey>.calendarEntry(navigator: Navigator) {
    entry<CalendarNavKey> {
        CalendarScreen()
    }
}
