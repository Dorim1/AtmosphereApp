package ru.anlyashenko.atmosphereapp

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.atmosphereapp.core.design_system.ui.NavigationBar
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.utils.LanguageManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.isLoading }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )

        val currentLocales = AppCompatDelegate.getApplicationLocales()
        if (currentLocales.isEmpty) {
            val defaultLang = LanguageManager.getDefaultLanguage()
            LanguageManager.setLanguage(defaultLang)
        }

        setContent {
            AtmosphereAppTheme(dynamicColor = false, darkTheme = false) {
                if (!viewModel.isLoading) {
                    NavigationBar(startDestination = viewModel.startDestination)
                }
            }
        }

    }
}

