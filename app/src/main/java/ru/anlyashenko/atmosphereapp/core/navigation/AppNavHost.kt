package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.anlyashenko.atmosphereapp.feature.calendar.ui.CalendarRoute
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeScreen
import ru.anlyashenko.atmosphereapp.feature.onboarding.ui.IntroScreen
import ru.anlyashenko.atmosphereapp.feature.profile.ui.ProfileRoute
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.AppearanceScreen
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui.EditMoodsScreen
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsScreen
import ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui.YearlyStatsScreen


@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.IntroRoute,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        modifier = modifier
    ) {
        composable<Destination.IntroRoute>() {
            IntroScreen(
                onGetInClick = {
                    navHostController.navigate(Destination.HomeRoute) {
                        popUpTo<Destination.IntroRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<Destination.HomeRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            HomeScreen()
        }

        composable<Destination.CalendarRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            CalendarRoute()
        }

        composable<Destination.UserRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            ProfileRoute(
                onNavigateToSettings = { navHostController.navigate(Destination.SettingsRoute) },
                onNavigateToYearlyStats = { navHostController.navigate(Destination.YearlyStatsRoute) }
            )
        }

        composable<Destination.YearlyStatsRoute> {
            YearlyStatsScreen(
                onNavigateBack = { navHostController.popBackStack() }
            )
        }

        composable<Destination.SettingsRoute> {
            SettingsScreen(
                onNavigateToAppearance = { navHostController.navigate(Destination.SettingsAppearanceRoute) },
                onNavigateToEditMoods = { navHostController.navigate(Destination.SettingsEditMoodsRoute) },
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Destination.SettingsAppearanceRoute> {
            AppearanceScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Destination.SettingsEditMoodsRoute> {
            EditMoodsScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}