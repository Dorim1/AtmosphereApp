package ru.anlyashenko.atmosphereapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.atmosphereapp.navigation.TOP_LEVEL_NAV_ITEMS
import ru.anlyashenko.core.navigation.NavigationState
import ru.anlyashenko.core.navigation.rememberNavigationState

@Composable
fun rememberAtmosphereAppState(
    startKey: NavKey
) : AtmosphereAppState {
    val topLevelKeys = remember(startKey) {
        TOP_LEVEL_NAV_ITEMS.keys + startKey
    }

    val navigationState = rememberNavigationState(startKey, topLevelKeys)

    return remember(navigationState) {
        AtmosphereAppState(navigationState)
    }
}

@Stable
class AtmosphereAppState(
    val navigationState: NavigationState
) {
    val currentTopLevelKey: NavKey
        get() = navigationState.currentTopLevelKey

    val shouldShowBottomBar: Boolean
        get() = TOP_LEVEL_NAV_ITEMS.containsKey(navigationState.currentKey)
}