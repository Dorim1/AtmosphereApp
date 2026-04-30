package ru.anlyashenko.atmosphereapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.core.model.ThemeMode
import ru.anlyashenko.core.presentation.language.LanguageManager

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
//        enableEdgeToEdge(SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.isLoading }

        val currentLocales = AppCompatDelegate.getApplicationLocales()
        if (currentLocales.isEmpty) {
            val defaultLang = LanguageManager.getDefaultLanguage()
            LanguageManager.setLanguage(defaultLang)
        }

        setContent {
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val cornerRadius by viewModel.cornerRadius.collectAsStateWithLifecycle()

            val isDarkTheme = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                else -> isSystemInDarkTheme()
            }

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme) {
                        SystemBarStyle.dark(Color.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(
                            Color.TRANSPARENT,
                            Color.TRANSPARENT
                        )
                    },
                    navigationBarStyle = if (isDarkTheme) {
                        SystemBarStyle.dark(Color.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(
                            Color.TRANSPARENT,
                            Color.TRANSPARENT
                        )
                    }
                )
                onDispose {}
            }

            AtmosphereAppTheme(
                themeMode = themeMode,
                cornerRadiusMode = cornerRadius
            ) {
                val startKey = viewModel.startDestination
                if (!viewModel.isLoading && startKey != null) {
                    AtmosphereApp(startKey = startKey)
                }
            }
        }

    }
}

