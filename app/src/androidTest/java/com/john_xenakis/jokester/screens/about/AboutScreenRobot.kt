package com.john_xenakis.jokester.screens.about

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.john_xenakis.jokester.MainActivity
import john_xenakis.jokester.BuildConfig

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
 * The connection link that is used for reaching and using AboutScreenRobot class
 * that in turn aboutScreenRobot function is used in About Screen tests.
 * @param composeTestRule A test rule made for using jetpack compose test functions
 * and testing the specified activity and its content.
 * @param func The robot test functions for about screen tests.
 * @return The About Screen Robot test class containing the robot test functions.
 */
internal fun aboutScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    func: AboutScreenRobot.() -> Unit
) = AboutScreenRobot(composeTestRule).also(func)

/**
 * A class that contains functions for using them with About Screen Tests.
 * Basically helping removing duplicate/boilerplate code.
 * @param composeTestRule A test rule made for using jetpack compose test functions
 * and testing the specified activity and its content.
 */
internal open class AboutScreenRobot constructor(private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    /**
     * Top app bar.
     * Node found with its content description.
     */
    private val nodeTopBar by lazy {
        composeTestRule.onNodeWithContentDescription("Top app bar")
    }

    /**
     * Top bar title text.
     * Node found with its text.
     */
    private val nodeTopBarTitleText by lazy {
        composeTestRule.onNodeWithText("About Jokester")
    }

    /**
     * Icon for top bar nav button.
     * Node found with its content description.
     */
    private val nodeTopBarNavIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Icon for navigation button")
    }

    /**
     * The "thank you" text.
     * Node found with its text.
     */
    private val nodeThankYouText by lazy {
        composeTestRule.onNodeWithText("Thank you for trying this app! I hope you have a good laugh!")
    }

    /**
     * The "whats new" text,
     * in "whats new" button.
     * Node found with its text.
     */
    private val nodeWhatsNewText by lazy {
        composeTestRule.onNodeWithText("What's New")
    }

    /**
     * The app version text,
     * in "whats new" button.
     * Node found with its text.
     */
    private val nodeAppVersion by lazy {
        composeTestRule.onNodeWithText(BuildConfig.VERSION_NAME)
    }

    /**
     * The icon for "whats new" button,
     * Node found with its content description.
     */
    private val nodeWhatsNewIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Icon for whats new button")
    }

    /**
     * The "whats new" button.
     * Node found with its content description.
     */
    private val nodeWhatsNewButton by lazy {
        composeTestRule.onNodeWithContentDescription("Whats new button")
    }

    /**
     * The divider line below "whats new" button.
     * Node found with its content description.
     */
    private val nodeDividerBelowWhatsNewButton by lazy {
        composeTestRule.onNodeWithContentDescription("Divider line below whats new button")
    }

    /**
     * The "whats new" dialog.
     * Node found with its content description.
     */
    private val nodeWhatsNewDialog by lazy {
        composeTestRule.onNodeWithContentDescription("Whats new dialog")
    }

    /**
     * The "whats new" dialog's surface.
     * Node found with its content description.
     */
    private val nodeWhatsNewDialogSurface by lazy {
        composeTestRule.onNodeWithContentDescription("Whats new dialog surface")
    }

    /**
     * The title text for "whats new" dialog.
     * Node found with its text.
     */
    private val nodeWhatsNewDialogTitle by lazy {
        composeTestRule.onNodeWithText("What's New:")
    }

    /**
     * The version name and app version date as a text,
     * which is in the "whats new" dialog.
     * Node found with its text.
     */
    private val nodeVersionNameAndAppVersionDate by lazy {
        composeTestRule.onNodeWithText(BuildConfig.VERSION_NAME + "" +
                " (" + BuildConfig.APP_VERSION_DATE + "):")
    }

    /**
     * The main "whats new" info text for the current app version,
     * which is in the "whats new" dialog.
     * Node found with its text.
     */
    private val nodeMainWhatsNewInfoText by lazy {
        composeTestRule.onNodeWithText("NEW:\n" +
                "• About Screen.\n" +
                "    • Whats new button with dialog. \n" +
                "• Filter jokes with tags, from home screen.\n" +
                "    • Safe jokes tag. \n" +
                "    • Other tags. \n" +
                "\n" +
                "REMOVE:\n" +
                "• \"Support me\" button, in side navigation drawer, from home screen.")
    }

    /**
     * The "All rights reserved" text.
     * The text is in the "whats new" dialog.
     * Node found with its text.
     */
    private val nodeAllRightsReservedText by lazy {
        composeTestRule.onNodeWithText("Jokester. \n" +
                "All Rights Reserved.")
    }

    /**
     * The back button on "whats new" dialog.
     * Node found with its content description.
     */
    private val nodeBackButtonWhatsNewDialog by lazy {
        composeTestRule.onNodeWithContentDescription("Back text button")
    }

    /**
     * The text on back button, from "whats new" dialog.
     * Node found with its text.
     */
    private val nodeTextOnBackButtonWhatsNewDialog by lazy {
        composeTestRule.onNodeWithText("Back")
    }

    /**
     * The "rate jokester" button.
     * Node found with its content description.
     */
    private val nodeRateButton by lazy {
        composeTestRule.onNodeWithContentDescription("Rate jokester text button")
    }

    /**
     * The text on "rate jokester" button.
     * Node found with its text.
     */
    private val nodeTextOnRateButton by lazy {
        composeTestRule.onNodeWithText("Rate Jokester")
    }

    /**
     * The icon on "rate jokester" button.
     * Node found with its content description.
     */
    private val nodeRateIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Icon for rate jokester button")
    }

    /**
     * The divider line below "rate jokester" button.
     * Node found with its content description.
     */
    private val nodeDividerBelowRateJokesterButton by lazy {
        composeTestRule.onNodeWithContentDescription("Divider line below rate jokester button")
    }

    /**
     * The "source code" button.
     * Node found with its content description.
     */
    private val nodeSourceCodeButton by lazy {
        composeTestRule.onNodeWithContentDescription("Source code button")
    }

    /**
     * The first text on "source code" button.
     * Node found with its text.
     */
    private val nodeFirstTextOnSourceCodeButton by lazy {
        composeTestRule.onNodeWithText("Source Code")
    }

    /**
     * The description text of "source code" button.
     * Node found with its text.
     */
    private val nodeDescriptionTextOnSourceCodeButton by lazy {
        composeTestRule.onNodeWithText("Jokester is an open-source free app licensed" +
                " under the GNU General Public License v3.0")
    }

    /**
     * The icon on "source code" button.
     * Node found with its content description.
     */
    private val nodeSourceCodeIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Icon on source code button")
    }

    /**
     * The contact developer button.
     * Node found with its content description.
     */
    private val nodeContactDeveloperButton by lazy {
        composeTestRule.onNodeWithContentDescription("Contact developer button")
    }

    /**
     * The text on "contact developer" button.
     * Node found with its text.
     */
    private val nodeContactDeveloperText by lazy {
        composeTestRule.onNodeWithText("Contact Developer")
    }

    /**
     * The icon on "contact developer" button.
     * Node found with its content description.
     */
    private val nodeContactDeveloperIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Icon on contact developer button")
    }

    /**
     * The divider line that is below the "source code" button.
     * Node found with its content description.
     */
    private val nodeDividerBelowSourceCode by lazy {
        composeTestRule.onNodeWithContentDescription("Divider below source code button")
    }

    /**
     * The test waits until a specified node with a content description exists,
     * and the test code continues.
     *
     * Also it has a timeout(the untilBoolean). If the node doesn't exist
     * and the waiting time exceeds the timeout, it fails and stops the test.
     * @param contentDescription The content description that the node is named.
     */
    fun waitUntilSeeNodeCd(contentDescription: String) {
        composeTestRule.waitUntil(3000L) {
            composeTestRule
                .onAllNodesWithContentDescription(label = contentDescription)
                .fetchSemanticsNodes().size == 1
        }
    }

    /**
     * The test waits until a specified node with a text exists,
     * and the test code continues.
     *
     * Also it has a timeout(the untilBoolean). If the node doesn't exist
     * and the waiting time exceeds the timeout, it fails and stops the test.
     * @param text The text that is displayed on screen.
     */
    fun waitUntilSeeNodeText(text: String) {
        composeTestRule.waitUntil(3000L) {
            composeTestRule
                .onAllNodesWithText(text = text)
                .fetchSemanticsNodes().size == 1
        }
    }

    /**
     * Clicks the "what's new" button.
     */
    fun waitUntilWhatsNewButtonClicked() {
        waitUntilSeeNodeCd("Whats new button")
        nodeWhatsNewButton.performClick()
    }

    /**
     * Waits until top bar asserts that is displayed.
     */
    fun waitUntilTopBarNavIconIsDisplayed() {
        waitUntilSeeNodeCd("Icon for navigation button")
        nodeTopBarNavIcon.assertIsDisplayed()
    }

    /**
     * Waits until title text asserts that is displayed.
     */
    fun waitUntilTopBarTitleTextIsDisplayed() {
        waitUntilSeeNodeText("About Jokester")
        nodeTopBarTitleText.assertIsDisplayed()
    }

    /**
     * Waits until top app bar asserts that is displayed.
     */
    fun waitUntilTopBarIsDisplayed() {
        waitUntilSeeNodeCd("Top app bar")
        nodeTopBar.assertIsDisplayed()
    }

    /**
     * Waits until "thank you" text asserts that it is displayed.
     */
    fun waitUntilThankYouTextIsDisplayed() {
        waitUntilSeeNodeText("Thank you for trying this app! I hope you have a good laugh!")
        nodeThankYouText.assertIsDisplayed()
    }

    /**
     * Waits until "whats new" text asserts that it is displayed.
     */
    fun waitUntilWhatsNewTextIsDisplayed() {
        waitUntilSeeNodeText("What's New")
        nodeWhatsNewText.assertIsDisplayed()
    }

    /**
     * Waits until app version text
     * asserts that it is displayed.
     */
    fun waitUntilAppVersionTextIsDisplayed() {
        waitUntilSeeNodeText(BuildConfig.VERSION_NAME)
        nodeAppVersion.assertIsDisplayed()
    }

    /**
     * Waits until "whats new" icon
     * asserts that it is displayed.
     */
    fun waitUntilWhatsNewIconIsDisplayed() {
        waitUntilSeeNodeCd("Icon for whats new button")
        nodeWhatsNewIcon.assertIsDisplayed()
    }

    /**
     * Waits until divider line below "whats new" button,
     * asserts that it is displayed.
     */
    fun waitUntilDividerBelowWhatsNewIsDisplayed() {
        waitUntilSeeNodeCd("Divider line below whats new button")
        nodeDividerBelowWhatsNewButton.assertIsDisplayed()
    }

    /**
     * Waits until "whats new" dialog,
     * asserts that it is displayed.
     */
    fun waitUntilWhatsNewDialogIsDisplayed() {
        waitUntilSeeNodeCd("Whats new dialog")
        nodeWhatsNewDialog.assertIsDisplayed()
    }

    /**
     * Waits until "whats new" dialog's surface,
     * asserts that it is displayed.
     */
    fun waitUntilWhatsNewDialogSurfaceIsDisplayed() {
        waitUntilSeeNodeCd("Whats new dialog surface")
        nodeWhatsNewDialogSurface.assertIsDisplayed()
    }

    /**
     * Waits until "whats new" title text,
     * asserts that it is displayed.
     */
    fun waitUntilWhatsNewDialogTitleIsDisplayed() {
        waitUntilSeeNodeText("What's New:")
        nodeWhatsNewDialogTitle.assertIsDisplayed()
    }

    /**
     * Waits until version name and app version date,
     * asserts that it is displayed.
     */
    fun waitUntilVersionNameAndAppVersionDateIsDisplayed() {
        waitUntilSeeNodeText(BuildConfig.VERSION_NAME + "" +
                " (" + BuildConfig.APP_VERSION_DATE + "):")
        nodeVersionNameAndAppVersionDate.assertIsDisplayed()
    }

    /**
     * Waits until main "whats new" info text,
     * asserts that it is displayed.
     */
    fun waitUntilMainWhatsNewInfoTextIsDisplayed() {
        waitUntilSeeNodeText("NEW:\n" +
                "• About Screen.\n" +
                "    • Whats new button with dialog. \n" +
                "• Filter jokes with tags, from home screen.\n" +
                "    • Safe jokes tag. \n" +
                "    • Other tags. \n" +
                "\n" +
                "REMOVE:\n" +
                "• \"Support me\" button, in side navigation drawer, from home screen.")
        nodeMainWhatsNewInfoText.assertIsDisplayed()
    }

    /**
     * Waits until "all rights reserved" text on "whats new" dialog,
     * asserts that it is displayed.
     */
    fun waitUntilAllRightsReservedTextIsDisplayed() {
        waitUntilSeeNodeText("Jokester. \n" +
                "All Rights Reserved.")
        nodeAllRightsReservedText.assertIsDisplayed()
    }

    /**
     * Waits until back button on "whats new" dialog,
     * asserts that it is displayed.
     */
    fun waitUntilBackButtonIsDisplayed() {
        waitUntilSeeNodeCd("Back text button")
        nodeBackButtonWhatsNewDialog.assertIsDisplayed()
    }

    /**
     * Waits until text on back button,
     * from "whats new" dialog,
     * asserts that it is displayed.
     */
    fun waitUntilTextOnBackButtonIsDisplayed() {
        waitUntilSeeNodeText("Back")
        nodeTextOnBackButtonWhatsNewDialog.assertIsDisplayed()
    }

    /**
     * Waits until text on "rate jokester" button,
     * asserts that it is displayed.
     */
    fun waitUntilTextOnRateButtonIsDisplayed() {
        waitUntilSeeNodeText("Rate Jokester")
        nodeTextOnRateButton.assertIsDisplayed()
    }

    /**
     * Waits until "rate jokester" button,
     * asserts that it is displayed.
     */
    fun waitUntilRateButtonIsDisplayed() {
        waitUntilSeeNodeCd("Rate jokester text button")
        nodeRateButton.assertIsDisplayed()
    }

    /**
     * Waits until icon on "rate jokester" button,
     * asserts that it is displayed.
     */
    fun waitUntilRateIconIsDisplayed() {
        waitUntilSeeNodeCd("Icon for rate jokester button")
        nodeRateIcon.assertIsDisplayed()
    }

    /**
     * Waits until divider below "rate jokester" button,
     * asserts that it is displayed.
     */
    fun waitUntilDividerBelowRateButtonIsDisplayed() {
        waitUntilSeeNodeCd("Divider line below rate jokester button")
        nodeDividerBelowRateJokesterButton.assertIsDisplayed()
    }

    /**
     * Waits until "source code" button,
     * asserts that it is displayed.
     */
    fun waitUntilSourceCodeButtonIsDisplayed() {
        waitUntilSeeNodeCd("Source code button")
        nodeSourceCodeButton.assertIsDisplayed()
    }

    /**
     * Waits until first text on "source code" button,
     * asserts that it is displayed.
     */
    fun waitUntilFirstTextOnSourceCodeButtonIsDisplayed() {
        waitUntilSeeNodeText("Source Code")
        nodeFirstTextOnSourceCodeButton.assertIsDisplayed()
    }

    /**
     * Waits until description text on "source code" button,
     * asserts that it is displayed.
     */
    fun waitUntilDescriptionTextOnSourceCodeButtonIsDisplayed() {
        waitUntilSeeNodeText("Jokester is an open-source free app licensed" +
                " under the GNU General Public License v3.0")
        nodeDescriptionTextOnSourceCodeButton.assertIsDisplayed()
    }

    /**
     * Waits until icon on "source code" button,
     * asserts that it is displayed.
     */
    fun waitUntilSourceCodeIconIsDisplayed() {
        waitUntilSeeNodeCd("Icon on source code button")
        nodeSourceCodeIcon.assertIsDisplayed()
    }

    /**
     * Waits until text on "contact developer" button,
     * asserts that it is displayed.
     */
    fun waitUntilContactDeveloperTextIsDisplayed() {
        waitUntilSeeNodeText("Contact Developer")
        nodeContactDeveloperText.assertIsDisplayed()
    }

    /**
     * Waits until icon on "contact developer" button,
     * asserts that it is displayed.
     */
    fun waitUntilContactDeveloperIconIsDisplayed() {
        waitUntilSeeNodeCd("Icon on contact developer button")
        nodeContactDeveloperIcon.assertIsDisplayed()
    }

    /**
     * Waits until "contact developer" button,
     * asserts that it is displayed.
     */
    fun waitUntilContactDeveloperButtonIsDisplayed() {
        waitUntilSeeNodeCd("Contact developer button")
        nodeContactDeveloperButton.assertIsDisplayed()
    }

    /**
     * Waits until divider line below "source code" button,
     * asserts that it is displayed.
     */
    fun waitUntilDividerBelowSourceCodeIsDisplayed() {
        waitUntilSeeNodeCd("Divider below source code button")
        nodeDividerBelowSourceCode.assertIsDisplayed()
    }
}