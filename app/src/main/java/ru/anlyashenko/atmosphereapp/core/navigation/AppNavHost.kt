package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.anlyashenko.feature.calendar.CalendarRoute
import ru.anlyashenko.feature.onboarding.IntroScreen
import ru.anlyashenko.feature.profile.ProfileRoute
import ru.anlyashenko.feature.settings.appearance.AppearanceScreen
import ru.anlyashenko.feature.settings.main.SettingsScreen
import ru.anlyashenko.feature.settings.moods.EditMoodsScreen
import ru.anlyashenko.feature.settings.notifications.NotificationSettingsRoute
import ru.anlyashenko.feature.yearlystats.YearlyStatsScreen
import ru.anlyashenko.features.home.HomeScreen


@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 3 },
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
        composable<Destination.IntroRoute> {
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
                onNavigateToYearlyStats = { navHostController.navigate(Destination.YearlyStatsRoute) },
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
                onNavigateToNotificationSettings = { navHostController.navigate(Destination.SettingsNotificationRoute) },
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

        composable<Destination.SettingsNotificationRoute> {
            NotificationSettingsRoute(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}