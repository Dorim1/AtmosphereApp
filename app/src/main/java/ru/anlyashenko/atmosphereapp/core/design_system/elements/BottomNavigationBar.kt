package ru.anlyashenko.atmosphereapp.core.design_system.elements

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.navigation.Destinations

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
    BottomNavItem("Home", Destinations.HomeRoute, (R.drawable.ic_home)),
    BottomNavItem("Calendar", Destinations.CalendarRoute, R.drawable.ic_calendar),
    BottomNavItem("User", Destinations.UserRoute, R.drawable.ic_user)
)

