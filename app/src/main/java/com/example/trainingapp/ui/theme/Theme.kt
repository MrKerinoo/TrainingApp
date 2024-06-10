package com.example.trainingapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Black80,
    secondary = Grey80,
    tertiary = Orange80,

    onPrimary = White80,
    onSecondary = DarkGrey,
    onTertiary = DarkOrange80,

    error = Red80,
    onError = DarkRed,

    primaryContainer = Green80,
    onPrimaryContainer = DarkGreen,
)

private val LightColorScheme = lightColorScheme(

    primary = White80,
    secondary = Black80,
    tertiary = Orange80,

    onPrimary = Black80,
    onSecondary = White80,
    onTertiary = DarkOrange80,

    error = Red80,
    onError = DarkRed,

    primaryContainer = Green80,
    onPrimaryContainer = DarkGreen,

)

@Composable
fun TrainingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
