package ru.anlyashenko.atmosphereapp.core.design_system.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.CornerRadiusMode
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = OnPrimaryWhite,

    background = BackgroundWhiteLight,
    onBackground = OnBackgroundBlackLight,

    secondaryContainer = ContainerWhiteLight,
    onSecondaryContainer = OnContainerGrayLight,

    secondary = SecondaryLight,
    onSecondary = OnSecondaryBlackLight,

    surface = SurfaceWhiteLight,
    onSurface = OnSurfaceBlackLight,

    error = WarningRed

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun AtmosphereAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    cornerRadiusMode: CornerRadiusMode = CornerRadiusMode.MODERATE,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val appShapes = Shapes(
        small = RoundedCornerShape(cornerRadiusMode.radius),
        medium = RoundedCornerShape(cornerRadiusMode.radius),
        large = RoundedCornerShape(cornerRadiusMode.radius)
    )


    MaterialTheme(
        colorScheme = colorScheme,
        shapes = appShapes,
        typography = Typography,
        content = content
    )
}