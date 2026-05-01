package ru.anlyashenko.atmosphereapp

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.core.navigation.toEntries
import ru.anlyashenko.feature.calendar.impl.navigation.calendarEntry
import ru.anlyashenko.feature.home.impl.navigation.homeEntry
import ru.anlyashenko.feature.onboarding.impl.navigation.introEntry
import ru.anlyashenko.feature.profile.impl.navigation.profileEntry
import ru.anlyashenko.feature.settings.impl.navigation.appearanceEntry
import ru.anlyashenko.feature.settings.impl.navigation.editMoodsEntry
import ru.anlyashenko.feature.settings.impl.navigation.notificationSettingsEntry
import ru.anlyashenko.feature.settings.impl.navigation.settingsEntry
import ru.anlyashenko.feature.yealystats.impl.navigation.yearlyStatsEntry

@Composable
fun AtmosphereApp(
    startKey: NavKey,
    modifier: Modifier = Modifier
) {
    val appState = rememberAtmosphereAppState(startKey)
    val navigator = remember { Navigator(appState.navigationState) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    windowInsets = NavigationBarDefaults.windowInsets,
                    modifier = modifier.height(67.dp)
                ) {
                    TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                        val selected = navKey == appState.currentTopLevelKey

                        NavigationBarItem(
                            selected = selected,
                            onClick = { navigator.navigate(navKey) },
                            icon = {
                                Icon(
                                    painter = painterResource(id = navItem.selectedIcon),
                                    contentDescription = navItem.titleTextId
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
            val entryProvider = entryProvider<NavKey> {
                introEntry(navigator)
                homeEntry(navigator)
                calendarEntry(navigator)
                profileEntry(navigator)

                yearlyStatsEntry(navigator)

                settingsEntry(navigator)
                appearanceEntry(navigator)
                editMoodsEntry(navigator)
                notificationSettingsEntry(navigator)
            }

            NavDisplay(
                entries = appState.navigationState.toEntries(entryProvider),
                onBack = { navigator.goBack() },
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
            )
    }
}