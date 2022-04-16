package com.john_xenakis.jokester.screens.about

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.john_xenakis.jokester.MainActivity
import com.john_xenakis.jokester.ui.theme.JokesterTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
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
 * UI/Instrumented tests, for testing About Screen.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@HiltAndroidTest
class AboutScreenTest {
    /**
     * Rule for hilt. Hilt rule should be created first,
     * before any other rules are created on the Home Screen Test class.
     */
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    /**
     * Rule for jetpack compose tests.
     */
    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * This function gets called once before each test.
     */
    @Before
    fun init() {
        hiltRule.inject()
        launchAboutScreen()
    }

    /**
     * Launches about screen.
     */
    private fun launchAboutScreen() {
        composeTestRule.activity.setContent {
            JokesterTheme {
                val navController = rememberNavController()
                AboutScreen(navController = navController)
            }
        }
    }

    /**
     * Tests that the title text on top app bar,
     * is displayed.
     */
    @Test
    fun testTopBarTitleIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilTopBarTitleTextIsDisplayed()
        }
    }

    /**
     * Tests that the navigation icon of navigation button,
     * on top app bar, is displayed.
     */
    @Test
    fun testTopBarNavIconIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilTopBarNavIconIsDisplayed()
        }
    }

    /**
     * Tests that the top app bar,
     * is displayed.
     */
    @Test
    fun testTopBarIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilTopBarIsDisplayed()
        }
    }

    /**
     * Tests that thank you text,
     * is displayed.
     */
    @Test
    fun testThankYouTextIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilThankYouTextIsDisplayed()
        }
    }

    /**
     * Tests that "what's new" text,
     * in "what's new" button,
     * is displayed.
     */
    @Test
    fun testWhatsNewTextIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewTextIsDisplayed()
        }
    }

    /**
     * Tests that app version text,
     * in "what's new" button,
     * is displayed.
     */
    @Test
    fun testAppVersionTextIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilAppVersionTextIsDisplayed()
        }
    }

    /**
     * Tests that icon for "what's new" button,
     * is displayed.
     */
    @Test
    fun testWhatsNewIconIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewIconIsDisplayed()
        }
    }

    /**
     * Tests that divider line below "whats new" button,
     * is displayed.
     */
    @Test
    fun testDividerLineBelowWhatsNewIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilDividerBelowWhatsNewIsDisplayed()
        }
    }

    /**
     * Tests that "what's new" dialog,
     * is displayed.
     */
    @Test
    fun testWhatsNewDialogIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilWhatsNewDialogIsDisplayed()
        }
    }

    /**
     * Tests that "what's new" dialog's surface,
     * is displayed.
     */
    @Test
    fun testWhatsNewDialogSurfaceIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilWhatsNewDialogSurfaceIsDisplayed()
        }
    }

    /**
     * Tests that title text on "what's new" dialog,
     * is displayed.
     */
    @Test
    fun testWhatsNewDialogTitleIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilWhatsNewDialogTitleIsDisplayed()
        }
    }

    /**
     * Tests that version name and app version date, on "what's new" dialog
     * is displayed.
     */
    @Test
    fun testVersionNameAndAppVersionDateIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilVersionNameAndAppVersionDateIsDisplayed()
        }
    }

    /**
     * Tests that the main "what's new" info text
     * (the release notes for the current app version),
     * on "what's new" dialog, is displayed.
     */
    @Test
    fun testWhatsNewInfoIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilMainWhatsNewInfoTextIsDisplayed()
        }
    }

    /**
     * Tests that the "all rights reserved" text,
     * on "what's new" dialog, is displayed.
     */
    @Test
    fun testAllRightsReservedTextIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilAllRightsReservedTextIsDisplayed()
        }
    }

    /**
     * Tests that the back text button,
     * on "what's new" dialog, is displayed.
     */
    @Test
    fun testBackButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilBackButtonIsDisplayed()
        }
    }

    /**
     * Tests that the text on back button,
     * from "whats new" dialog,
     * is displayed.
     */
    @Test
    fun testTextOnBackButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilWhatsNewButtonClicked()
            waitUntilTextOnBackButtonIsDisplayed()
        }
    }

    /**
     * Tests that the text on "rate jokester" button,
     * is displayed.
     */
    @Test
    fun testTextOnRateButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilTextOnRateButtonIsDisplayed()
        }
    }

    /**
     * Tests that the "rate jokester" button,
     * is displayed.
     */
    @Test
    fun testRateButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilRateButtonIsDisplayed()
        }
    }

    /**
     * Tests that the icon on "rate jokester" button,
     * is displayed.
     */
    @Test
    fun testRateIconIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilRateIconIsDisplayed()
        }
    }

    /**
     * Tests that the divider line below "rate jokester" button,
     * is displayed.
     */
    @Test
    fun testDividerBelowRateButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilDividerBelowRateButtonIsDisplayed()
        }
    }

    /**
     * Tests that "source code" button,
     * is displayed.
     */
    @Test
    fun testSourceCodeButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilSourceCodeButtonIsDisplayed()
        }
    }

    /**
     * Tests that first text
     * on "source code" button,
     * is displayed.
     */
    @Test
    fun testFirstTextOnSourceCodeButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilFirstTextOnSourceCodeButtonIsDisplayed()
        }
    }

    /**
     * Tests that description text
     * on "source code" button,
     * is displayed.
     */
    @Test
    fun testDescriptionTextOnSourceCodeButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilDescriptionTextOnSourceCodeButtonIsDisplayed()
        }
    }

    /**
     * Tests that icon
     * on "source code" button,
     * is displayed.
     */
    @Test
    fun testSourceCodeIconIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilSourceCodeIconIsDisplayed()
        }
    }

    /**
     * Tests that the "contact developer" button,
     * is displayed.
     */
    @Test
    fun testContactDeveloperButtonIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilContactDeveloperButtonIsDisplayed()
        }
    }

    /**
     * Tests that text
     * on "contact developer" button,
     * is displayed.
     */
    @Test
    fun testContactDeveloperTextIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilContactDeveloperTextIsDisplayed()
        }
    }

    /**
     * Tests that icon
     * on "contact developer" button,
     * is displayed.
     */
    @Test
    fun testContactDeveloperIconIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilContactDeveloperIconIsDisplayed()
        }
    }

    /**
     * Tests that divider line
     * below "source code" button,
     * is displayed.
     */
    @Test
    fun testDividerBelowSourceCodeIsDisplayed() {
        aboutScreenRobot(composeTestRule) {
            waitUntilDividerBelowSourceCodeIsDisplayed()
        }
    }
}