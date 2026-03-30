package ru.anlyashenko.atmosphereapp.core.navigation

import kotlinx.serialization.Serializable


sealed class Destinations {
    @Serializable
    data object IntroRoute
    @Serializable
    data object HomeRoute

    @Serializable
    data object CalendarRoute

    @Serializable
    data object UserRoute

    @Serializable
    data object YearlyStatsRoute
    @Serializable
    data object SettingsRoute
}