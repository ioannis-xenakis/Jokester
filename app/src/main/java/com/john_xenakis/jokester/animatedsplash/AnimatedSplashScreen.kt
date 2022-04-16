package com.john_xenakis.jokester.animatedsplash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.john_xenakis.jokester.ui.theme.*
import john_xenakis.jokester.R
import kotlinx.coroutines.delay

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
 * The full animated splash screen.
 */
@Composable
fun AnimatedSplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                delayMillis = 200,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(1000L)
        navController.popBackStack()
        navController.navigate("home_screen")
    }
    Splash(scale.value)
}

/**
 * The splash screen with its items and with no animation.
 */
@Composable
fun Splash(scaleValue: Float) {
    Box(modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center) {

        val isDarkTheme = isSystemInDarkTheme()

        Column {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier
                    .size(160.dp)
                    .scale(scale = scaleValue)
                    .semantics { contentDescription = "Background 1st circle" }) {
                    drawCircle(color = if (isDarkTheme) Gray53 else SemiTranspBlue65)
                }

                Canvas(
                    modifier = Modifier
                        .size(104.dp)
                        .scale(scale = scaleValue)
                        .semantics { contentDescription = "Foreground 2nd circle" }
                ) {
                    drawCircle(color = if (isDarkTheme) Gray80 else Blue65)
                }

                Icon(
                    painter = painterResource(id = R.drawable.laughing_icon),
                    contentDescription = "Laughing icon", modifier = Modifier
                        .size(104.dp)
                        .scale(scale = scaleValue),
                    tint = NoColor
                )
            }

            Text(
                text = "Jokester",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 19.dp)
                    .scale(scale = scaleValue),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * The Light mode preview of how the splash screen looks.
 */
@Composable
@Preview(showSystemUi = true)
fun SplashScreenPreview() {
    Splash(1f)
}

/**
 * The Dark mode preview of how the splash screen looks.
 */
@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
fun SplashScreenDarkPreview() {
    Splash(1f)
}
