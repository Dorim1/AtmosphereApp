package ru.anlyashenko.atmosphereapp.core.navigation

import kotlinx.serialization.Serializable


sealed interface Destination {
    @Serializable
    data object IntroRoute : Destination

    @Serializable
    data object HomeRoute : Destination

    @Serializable
    data object CalendarRoute : Destination

    @Serializable
    data object UserRoute : Destination

    @Serializable
    data object YearlyStatsRoute : Destination
    @Serializable
    data object SettingsRoute : Destination

    @Serializable
    data object SettingsAppearanceRoute : Destination

    @Serializable
    data object SettingsEditMoodsRoute : Destination
}