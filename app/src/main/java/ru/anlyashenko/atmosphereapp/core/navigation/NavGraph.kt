package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Home")
            }
        }
    }
}