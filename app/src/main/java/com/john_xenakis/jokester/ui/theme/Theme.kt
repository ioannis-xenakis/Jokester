package com.john_xenakis.jokester.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/*
    Jokester is the app for reading jokes and make people laugh.
    Copyright (C) 2022  Ioannis Xenakis

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Anything you want to contact me for, contact me with an e-mail, at: xenakis.i.contact@gmail.com
    I'll be happy to help you, or discuss anything with you!
 */

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