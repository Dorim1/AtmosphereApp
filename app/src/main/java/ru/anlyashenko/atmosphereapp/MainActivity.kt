package ru.anlyashenko.atmosphereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import ru.anlyashenko.atmosphereapp.core.design_system.elements.NavigationBar2
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.navigation.AppNavHost
import ru.anlyashenko.atmosphereapp.core.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            AtmosphereAppTheme(dynamicColor = false, darkTheme = false) {
//                val navController = rememberNavController()
//                AppNavHost(navController)
//                AppNavigation()
                NavigationBar2()
            }
        }
    }
}

