package ru.anlyashenko.atmosphereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.navigation.AppNavigation
import ru.anlyashenko.atmosphereapp.core.navigation.Screens
import ru.anlyashenko.atmosphereapp.feature.onboarding.ui.IntroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            AtmosphereAppTheme {
                AppNavigation()
            }
        }
    }
}

