package ru.anlyashenko.feature.home.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.anlyashenko.core.navigation.Navigator

@Serializable
object HomeNavKey : NavKey {
}

fun Navigator.navigateToHome() {
    navigate(HomeNavKey)
}