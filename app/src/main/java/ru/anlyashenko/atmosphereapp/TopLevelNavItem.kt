package ru.anlyashenko.atmosphereapp

import ru.anlyashenko.feature.calendar.api.CalendarNavKey
import ru.anlyashenko.feature.home.api.HomeNavKey
import ru.anlyashenko.feature.profile.api.ProfileNavKey

data class TopLevelNavItem(
    val selectedIcon: Int,
    val titleTextId: String
)

val HOME = TopLevelNavItem(
    selectedIcon = R.drawable.ic_home,
    titleTextId = "Home"
)

val CALENDAR = TopLevelNavItem(
    selectedIcon = R.drawable.ic_calendar,
    titleTextId = "Calendar"
)

val PROFILE = TopLevelNavItem(
    selectedIcon = R.drawable.ic_user,
    titleTextId = "Profile"
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    CalendarNavKey to CALENDAR,
    ProfileNavKey to PROFILE,
)