package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import ru.anlyashenko.atmosphereapp.core.designsystem.elements.BottomNavigationBar
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeScreen
import ru.anlyashenko.atmosphereapp.feature.onboarding.ui.IntroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screensWithoutBottomBar = listOf(
        Destinations.IntroRoute::class.qualifiedName
    )

    Scaffold(
        bottomBar = {
            if (currentDestination?.route !in screensWithoutBottomBar)
                BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Destinations.IntroRoute,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable<Destinations.IntroRoute> {
                IntroScreen(
                    onGetInClick = {
                        navController.navigate(Destinations.HomeRoute) {
                            popUpTo<Destinations.IntroRoute> { inclusive = true }
                        }
                    }
                )
            }

            composable<Destinations.HomeRoute> {
                HomeScreen()
            }

            composable<Destinations.CalendarRoute> {
                Text("Экран Календаря", modifier = Modifier.fillMaxSize())
            }

            composable<Destinations.UserRoute> {
                Text("Экран пользователя", modifier = Modifier.fillMaxSize())
            }
        }
    }
}