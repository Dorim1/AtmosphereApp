package ru.anlyashenko.atmosphereapp.core.navigation

import kotlinx.serialization.Serializable


sealed class Destinations(val route: String) {
    @Serializable
    object IntroRoute
    @Serializable
    object HomeRoute

    @Serializable
    object CalendarRoute

    @Serializable
    object UserRoute
}