package ru.anlyashenko.atmosphereapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.core.navigation.rememberNavigationState
import ru.anlyashenko.core.navigation.toEntries
import ru.anlyashenko.feature.home.api.HomeNavKey
import ru.anlyashenko.feature.home.impl.navigation.homeEntry
import ru.anlyashenko.feature.onboarding.api.IntroNavKey
import ru.anlyashenko.feature.onboarding.impl.navigation.introEntry

@Composable
fun AtmosphereApp() {
    val topLevelKeys = setOf<NavKey>(IntroNavKey, HomeNavKey)

    val navigationState = rememberNavigationState(
        startKey = IntroNavKey,
        topLevelKeys = topLevelKeys
    )

    val navigator = remember { Navigator(navigationState) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val entryProvider = entryProvider<NavKey> {
            introEntry(navigator)
            homeEntry(navigator)
        }

        NavDisplay(
            entries = navigationState.toEntries(entryProvider),
            onBack = { navigator.goBack() }
        )
    }
}