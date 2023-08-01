package com.kiarielinus.notes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    background = BackgroundLightColor,
    surface = SurfaceLightColor,
)

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    background = BackgroundDarkColor,
    surface = SurfaceDarkColor,
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //Remembers a SystemUiController for the given window
    val systemUiController = rememberSystemUiController()

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

    SideEffect {
        systemUiController.setStatusBarColor(
            color = colors.background,
            darkIcons = !darkTheme,
        )
    }
}