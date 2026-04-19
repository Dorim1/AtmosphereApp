package ru.anlyashenko.atmosphereapp.core.design_system.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.navigation.AppNavHost
import ru.anlyashenko.atmosphereapp.core.navigation.Destination

data class BottomNavItem(
    val title: String,
    val route: Any,
    val icon: Int
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Destination.HomeRoute, (R.drawable.ic_home)),
    BottomNavItem("Calendar", Destination.CalendarRoute, R.drawable.ic_calendar),
    BottomNavItem("User", Destination.UserRoute, R.drawable.ic_user)
)

@Composable
fun NavigationBar(modifier: Modifier = Modifier, startDestination: Destination) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        dest.hasRoute(Destination.HomeRoute::class) ||
                dest.hasRoute(Destination.CalendarRoute::class) ||
                dest.hasRoute(Destination.UserRoute::class)
    } == true

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    windowInsets = NavigationBarDefaults.windowInsets,
                    modifier = modifier.height(67.dp
                    )
                ) {
                    bottomNavItems.forEach { destination ->
                        val isSelected = currentDestination.hierarchy.any {
                            it.hasRoute(destination.route::class)
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painterResource(destination.icon),
                                    contentDescription = destination.title
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }

        }
    ) { contentPadding ->
        AppNavHost(
            navHostController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(bottom = contentPadding.calculateBottomPadding())
        )
    }
}

