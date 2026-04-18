package ru.anlyashenko.atmosphereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.atmosphereapp.core.design_system.ui.NavigationBar
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.utils.LanguageManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )

        // todo: че нибудь придумать с дефолт языком
        val currentLocales = AppCompatDelegate.getApplicationLocales()

        if (currentLocales.isEmpty) {
            val defaultLang = LanguageManager.getDefaultLanguage()
            LanguageManager.setLanguage(defaultLang)
        }

        setContent {
            AtmosphereAppTheme(dynamicColor = false, darkTheme = false) {
                NavigationBar()
            }
        }

    }
}

