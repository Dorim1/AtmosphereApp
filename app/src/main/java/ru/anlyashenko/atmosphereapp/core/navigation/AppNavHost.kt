package ru.anlyashenko.atmosphereapp.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.json.Json
import ru.anlyashenko.atmosphereapp.core.dispathchers.DefaultDispatcherProvider
import ru.anlyashenko.atmosphereapp.data.network.WeatherApi
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepository
import ru.anlyashenko.atmosphereapp.feature.calendar.ui.CalendarScreen
import ru.anlyashenko.atmosphereapp.feature.calendar.ui.mockMoodMap
import ru.anlyashenko.atmosphereapp.feature.calendar.ui.mockNote
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeScreen
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeViewModel
import ru.anlyashenko.atmosphereapp.feature.onboarding.ui.IntroScreen
import ru.anlyashenko.atmosphereapp.feature.profile.ui.ProfileScreen
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsScreen
import ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui.YearlyStatsScreen
import java.time.LocalDate


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

        // Мок-данные
        composable<Destination.CalendarRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            CalendarScreen(
                selectedDate = LocalDate.of(2026, 3, 11),
                moodMap = mockMoodMap,
                note = mockNote,
                onDateClick = { },
                onDeleteNote = { }
            )
        }

        composable<Destination.UserRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            ProfileScreen(
                totalEntries = 64,
                currentStreak = 27,
                longestStreak = 36,
                onYearlyStatsClick = {
                    navHostController.navigate(Destination.YearlyStatsRoute)
                },
                onSettingsClick = {
                    navHostController.navigate(Destination.SettingsRoute)
                }
            )
        }

        composable<Destination.YearlyStatsRoute> {
            YearlyStatsScreen(
                moodMap = mockMoodMap
            )
        }

        composable<Destination.SettingsRoute> {
            SettingsScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}