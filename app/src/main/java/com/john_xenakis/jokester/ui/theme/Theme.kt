package com.john_xenakis.jokester.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * The color palette for when the app is in dark theme mode.
 */
private val DarkColorPalette = darkColorScheme(
    primary = Gray80,
    onPrimary = Gray00,
    background = Gray27,
    onBackground = Gray100,
    error = Red40,
    onError = Gray00
)

/**
 * The color palette for when the app is in light theme mode.
 */
private val LightColorPalette = lightColorScheme(
    primary = Blue65,
    onPrimary = Gray100,
    background = Gray100,
    onBackground = Gray00,
    error = Red50,
    onError = Gray100
)

/**
 * The apps ui theme. This is where all theme attributes, gets connected and used.
 */
@Composable
fun JokesterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {

    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = FredokaOneTypography,
        shapes = Shapes
    )
}