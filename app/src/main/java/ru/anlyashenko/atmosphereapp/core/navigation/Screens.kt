package ru.anlyashenko.atmosphereapp.core.navigation

import kotlinx.serialization.Serializable


sealed class Screens {
    @Serializable
    object IntroRoute
    @Serializable
    object HomeRoute
}