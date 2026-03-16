package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeScreen
import ru.anlyashenko.atmosphereapp.feature.onboarding.ui.IntroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.IntroRoute
    ) {
        composable<Screens.IntroRoute> {
            IntroScreen(
                onGetInClick = {
                    navController.navigate(Screens.HomeRoute) {
                        popUpTo<Screens.IntroRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.HomeRoute> {
            HomeScreen()
        }
    }
}