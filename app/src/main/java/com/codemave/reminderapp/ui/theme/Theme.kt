package com.codemave.reminderapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = CustomBlue,
    primaryVariant = CustomSkyBlue,
    secondary = CustomSkyBlue
)

private val LightColorPalette = lightColors(
    primary = CustomBlue,
    primaryVariant = CustomSkyBlue,
    secondary = CustomSkyBlue

)

@Composable
fun MobileComputingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}