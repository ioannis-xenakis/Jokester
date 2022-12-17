package com.john_xenakis.jokester

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.john_xenakis.jokester.animatedsplash.AnimatedSplashScreen
import com.john_xenakis.jokester.ui.theme.JokesterTheme
import org.junit.Rule
import org.junit.Test

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
 * Ui test with Jetpack compose, for testing Splash Screen.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class SplashScreenTest {
    /**
     * Jetpack compose rule, for managing and executing screen specific commands,
     * for the splash screen.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Launches the app and the splash screen.
     */
    private fun launchSplashScreen() {
        composeTestRule.setContent {
            JokesterTheme {
                val navController = rememberNavController()
                AnimatedSplashScreen(navController = navController)
            }
        }
    }

    /**
     * Ui test for checking if app title text("Jokester" text),
     * is displayed on screen.
     */
    @Test
    fun appTitleTextIsDisplayed() {
        launchSplashScreen()
        composeTestRule.onNodeWithText("Jokester").assertIsDisplayed()
    }

    /**
     * Ui test for checking if background first circle,
     * is displayed on screen.
     */
    @Test
    fun backgroundFirstCircleIsDisplayed() {
        launchSplashScreen()
        composeTestRule
            .onNodeWithContentDescription("Background 1st circle")
            .assertIsDisplayed()
    }

    /**
     * Ui test for checking if foreground second circle,
     * is displayed on screen.
     */
    @Test
    fun foregroundSecondCircleIsDisplayed() {
        launchSplashScreen()
        composeTestRule
            .onNodeWithContentDescription("Foreground 2nd circle")
            .assertIsDisplayed()
    }

    /**
     * Ui test for checking if "laughing face" icon,
     * is displayed on screen.
     */
    @Test
    fun laughingIconIsDisplayed() {
        launchSplashScreen()
        composeTestRule
            .onNodeWithContentDescription("Laughing icon")
            .assertIsDisplayed()
    }
}