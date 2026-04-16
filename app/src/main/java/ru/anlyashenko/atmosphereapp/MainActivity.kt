package ru.anlyashenko.atmosphereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import ru.anlyashenko.atmosphereapp.core.design_system.ui.NavigationBar
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme

@AndroidEntryPoint
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
                NavigationBar()
            }
        }

    }
}

