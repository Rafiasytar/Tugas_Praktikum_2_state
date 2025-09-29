package com.example.praktikum2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Dark mode
private val DarkColorScheme = darkColorScheme(
    primary = HubBlueDark,
    onPrimary = HubOnBlueDark,
    background = HubBackgroundDark,
    onBackground = HubOnBackgroundDark,
    surface = HubSurfaceDark,
    onSurface = HubOnSurfaceDark
)

// Light mode
private val LightColorScheme = lightColorScheme(
    primary = HubBlue,
    onPrimary = HubOnBlue,
    background = HubBackgroundLight,
    onBackground = HubOnBackgroundLight,
    surface = HubSurfaceLight,
    onSurface = HubOnSurfaceLight
)

@Composable
fun Praktikum2Theme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
