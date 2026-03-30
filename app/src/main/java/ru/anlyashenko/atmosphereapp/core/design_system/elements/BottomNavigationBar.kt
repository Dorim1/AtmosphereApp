package ru.anlyashenko.atmosphereapp.core.design_system.elements

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.navigation.AppNavHost
import ru.anlyashenko.atmosphereapp.core.navigation.Destination

@Composable
fun BottomNavigationBar(navController: NavController) {
    //
    val selectedNavigationIndex = rememberSaveable() {
        mutableIntStateOf(0)
    }
    //
    /*NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MainTitleColorLight
                )
            )
        }*/

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = { Icon(painterResource(item.icon), contentDescription = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MainTitleColorLight
                )
            )
        }
    }

}

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
fun NavigationBar2(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        dest.hasRoute(Destination.HomeRoute::class) ||
        dest.hasRoute(Destination.CalendarRoute::class) ||
        dest.hasRoute(Destination.UserRoute::class)
    } == true

    val selectedNavigationIndex = rememberSaveable() { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    bottomNavItems.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedNavigationIndex.intValue == index,
                            onClick = {
                                selectedNavigationIndex.intValue = index
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
            modifier = Modifier.padding(contentPadding)
        )
    }
}

