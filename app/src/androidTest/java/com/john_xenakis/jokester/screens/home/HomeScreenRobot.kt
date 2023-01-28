package com.john_xenakis.jokester.screens.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.john_xenakis.jokester.MainActivity

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
 * The connection link that is used for reaching and using HomeScreenRobot class
 * that in turn homeScreenRobot function is used in Home Screen tests.
 * @param composeTestRule A test rule made for using jetpack compose test functions
 * and testing the specified activity and its content.
 * @param func The robot test functions for home screen tests.
 * @return The Home Screen Robot test class containing the robot test functions.
 */
internal fun homeScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    func: HomeScreenRobot.() -> Unit
) = HomeScreenRobot(composeTestRule).also(func)

/**
 * A class that contains functions for using them with Home Screen Tests.
 * Basically helping removing duplicate/boilerplate code.
 * @param composeTestRule A test rule made for using jetpack compose test functions
 * and testing the specified activity and its content.
 */
internal open class HomeScreenRobot constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    /**
     * The joke horizontal pager ui element(node) for testing usage. Node found with its content description.
     */
    private val jokeHorizontalPager by lazy {
        composeTestRule.onNodeWithContentDescription("Joke horizontal pager")
    }

    /**
     * The circular joke progress indicator ui element(node) for testing usage. Node found with its content description.
     */
    private val nodeJokeIndicator by lazy {
        composeTestRule.onNodeWithContentDescription("Joke progress indicator")
    }

    /**
     * The error code ui element(node) for testing usage. Node found with its content description.
     */
    private val nodeErrorCode by lazy {
        composeTestRule.onNodeWithContentDescription("Error code")
    }

    /**
     * The error message ui element(node) for testing usage. Node found with its content description.
     */
    private val nodeErrorMessage by lazy {
        composeTestRule.onNodeWithContentDescription("Error text message")
    }

    /**
     * The top app bar ui element(node) for testing usage. Node found with its content description.
     */
    private val nodeTopBar by lazy {
        composeTestRule.onNodeWithContentDescription("Center aligned top app bar")
    }

    /**
     * The top app bar title text ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeTopBarTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Title text on top app bar")
    }

    /**
     * The top app bar navigation button, ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeNavButton by lazy {
        composeTestRule.onNodeWithContentDescription("Navigation button on top app bar")
    }

    /**
     * The top app bar navigation icon, ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeNavIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Navigation icon for navigation button")
    }

    /**
     * The navigation drawer, ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeNavDrawer by lazy {
        composeTestRule.onNodeWithContentDescription("Side navigation drawer")
    }

    /**
     * The navigation sheet which is in the navigation drawer,
     * ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeNavSheet by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer sheet for navigation drawer")
    }

    /**
     * The drawer column with its padding,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeDrawerColumn by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer column")
    }

    /**
     * The laughing face icon,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeDrawerLaughingIcon by lazy {
        composeTestRule.onNodeWithContentDescription("Laughing face icon in drawer")
    }

    /**
     * The drawer title,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeDrawerTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer title")
    }

    /**
     * The drawer title text,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeDrawerTitleText by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer title text")
    }

    /**
     * The categories group title text,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeCategoriesGroupTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer categories group title text")
    }

    /**
     * The group title text named "other",
     * which is in the navigation drawer
     * and is a ui element(node), which is used for testing usage.
     * Node found with its content description.
     */
    private val nodeOtherGroupTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Drawer other group title text")
    }

    /**
     * The joke categories error text,
     * which is in the navigation drawer
     * and is a ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeCategoriesErrorText by lazy {
        composeTestRule.onNodeWithContentDescription(
            "Joke categories error text, in navigation drawer"
        )
    }

    /**
     * The support me button,
     * which is in the navigation drawer
     * and is a ui element(node) which is used for testing.
     * Node found with its content description.
     */
    private val nodeSupportMeButton by lazy {
        composeTestRule.onNodeWithContentDescription(
            "Support me button, in navigation drawer"
        )
    }

    /**
     * The dollar icon for the support me button,
     * which is in the navigation drawer
     * and is a ui element(node) which is used for testing.
     * Node found with its content description.
     */
    private val nodeIconForSupportMeButton by lazy {
        composeTestRule.onNodeWithContentDescription(
            "Icon for support me button"
        )
    }

    /**
     * The text for the support me button,
     * which is in the navigation drawer
     * and is a ui element(node) which is used for testing.
     * Node found with its content description.
     */
    private val nodeTextForSupportMeButton by lazy {
        composeTestRule.onNodeWithContentDescription(
            "Text for support me button"
        )
    }

    /**
     * The drawer items divider line(gray horizontal line),
     * which separates the joke categories items,
     * from the other drawer items
     * and is in the navigation drawer.
     * The divider line is a ui element(node) for testing usage
     * and is found with its content description.
     */
    private val nodeDrawerItemDivider by lazy {
        composeTestRule.onNodeWithContentDescription(
            "Drawer items divider"
        )
    }

    /**
     * The previous joke button ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodePreviousJoke by lazy {
        composeTestRule.onNodeWithContentDescription("Previous joke button")
    }

    /**
     * The next joke button ui element(node) for testing usage.
     * Node found with its content description.
     */
    private val nodeNextJoke by lazy {
        composeTestRule.onNodeWithContentDescription("Next joke button")
    }

    /**
     * The next joke button ui element(node) for testing usage. Node found with its tag.
     */
    private val nodeJokeBox0 by lazy {
        composeTestRule.onNodeWithTag("Joke text box 0")
    }

    /**
     * The joke text ui element(node) for testing usage. Node found with its tag.
     */
    private val nodeJokeText0 by lazy {
        composeTestRule.onNodeWithTag("Joke text 0")
    }

    /**
     * The error box(error container with all error info and button) ui element(node),
     * for testing usage.
     * Node found with its tag.
     */
    private val nodeErrorBox by lazy {
        composeTestRule.onNodeWithTag("Error box")
    }

    /**
     * The test waits until a specified node with a tag exists,
     * and the test code continues.
     *
     * Also it has a timeout. If the node doesn't exist(the untilBoolean)
     * and the waiting time exceeds the timeout, it fails and stops the test.
     * @param testTag The tag that the node is named.
     */
    private fun waitUntilSeeNodeTag(testTag: String) {
        composeTestRule.waitUntil(10000L) {
            composeTestRule.onAllNodesWithTag(testTag).fetchSemanticsNodes().size == 1
        }
    }

    /**
     * The test waits until a specified node with a content description exists,
     * and the test code continues.
     *
     * Also it has a timeout. If the node doesn't exist(the untilBoolean)
     * and the waiting time exceeds the timeout, it fails and stops the test.
     * @param contentDescription The content description that the node is named.
     */
    private fun waitUntilSeeNodeCd(contentDescription: String) {
        composeTestRule.waitUntil(3000L) {
            composeTestRule.onAllNodesWithContentDescription(label = contentDescription).fetchSemanticsNodes().size == 1
        }
    }

    /**
     * Asserts the test that the joke horizontal pager is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedHorizontalPager() = jokeHorizontalPager.assertIsDisplayed()

    /**
     * Asserts the test that the circular joke progress indicator, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedProgressIndicator() = nodeJokeIndicator.assertIsDisplayed()

    /**
     * Asserts the test that the top app bar is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedTopBar() = nodeTopBar.assertIsDisplayed()

    /**
     * Asserts the test that the top app bar title text, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedTopBarTitle() = nodeTopBarTitle.assertIsDisplayed()

    /**
     * Asserts the test that the top app bar nav button, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedNavButton() = nodeNavButton.assertIsDisplayed()

    /**
     * Asserts the test that the nav icon from nav button, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedNavIcon() = nodeNavIcon.assertIsDisplayed()

    /**
     * Asserts the test that the previous joke button, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedPreviousJoke() = nodePreviousJoke.assertIsDisplayed()

    /**
     * Asserts the test that the next joke button, is displayed on screen.
     * If it is not displayed, the test is failed.
     */
    fun displayedNextJoke() = nodeNextJoke.assertIsDisplayed()

    /**
     * Asserts the test that the drawer column inside navigation drawer,
     * is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedDrawerColumn() = nodeDrawerColumn.assertIsDisplayed()

    /**
     * Asserts the test that the categories group title text,
     * inside navigation drawer, is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedCategoriesGroupTitle() = nodeCategoriesGroupTitle.assertIsDisplayed()

    /**
     * Asserts the test that the other group title text,
     * inside navigation drawer, is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedOtherGroupTitle() = nodeOtherGroupTitle.assertIsDisplayed()

    /**
     * Asserts the test that the support me button,
     * inside navigation drawer, is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedSupportMeButton() = nodeSupportMeButton.assertIsDisplayed()

    /**
     * Asserts the test that the icon for the support me button,
     * inside navigation drawer, is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedSupportMeIcon() = nodeIconForSupportMeButton.assertIsDisplayed()

    /**
     * Asserts the test that the icon for the support me button,
     * inside navigation drawer, is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedSupportMeText() = nodeTextForSupportMeButton.assertIsDisplayed()

    /**
     * Asserts the test that the laughing face icon inside navigation drawer,
     * is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedDrawerLaughingIcon() = nodeDrawerLaughingIcon.assertIsDisplayed()

    /**
     * Asserts the test that the drawer title, inside navigation drawer,
     * is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedDrawerTitle() = nodeDrawerTitle.assertIsDisplayed()

    /**
     * Asserts the test that drawer title text, inside navigation drawer,
     * is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedDrawerTitleText() = nodeDrawerTitleText.assertIsDisplayed()

    /**
     * Asserts the test that the drawer items divider line,
     * inside navigation drawer,
     * is displayed on screen.
     * If it is not displayed, the test fails.
     */
    fun displayedDrawerItemsDivider() = nodeDrawerItemDivider.assertIsDisplayed()

    /**
     * Waits until joke box(background box and text) is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilJokeDisplayed() {
        waitUntilSeeNodeTag("Joke text box 0")
        nodeJokeBox0.assertIsDisplayed()
    }

    /**
     * Waits until joke text(text only and not the background box)
     * is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilJokeTextDisplayed() {
        waitUntilSeeNodeTag("Joke text 0")
        nodeJokeText0.assertIsDisplayed()
    }

    /**
     * Waits until error box(error container box with all error info and button), is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorDisplayed() {
        waitUntilSeeNodeTag("Error box")
        nodeErrorBox.assertIsDisplayed()
    }

    /**
     * Waits until error code number(basically a text together with its error code number) is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorCodeEquals() {
        waitUntilSeeNodeCd("Error code")
        nodeErrorCode.assertTextEquals("Error code: 106")
    }

    /**
     * Waits until error message text is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorMessageEquals() {
        waitUntilSeeNodeCd("Error text message")
        nodeErrorMessage.assertTextEquals("No matching joke found")
    }

    /**
     * Waits until side navigation drawer is seen on screen
     * and then asserts the test that it is displayed.
     */
    fun waitUntilNavDrawerDisplayed() {
        waitUntilSeeNodeCd("Side navigation drawer")
        nodeNavDrawer.assertIsDisplayed()
    }

    /**
     * Waits until side navigation sheet for navigation drawer is seen on screen
     * and then asserts the test that it is displayed.
     */
    fun waitUntilNavSheetDisplayed() {
        waitUntilSeeNodeCd("Drawer sheet for navigation drawer")
        nodeNavSheet.assertIsDisplayed()
    }

    /**
     * Waits until navigation button(menu button) is seen on top app bar, on screen
     * and then clicks the button.
     */
    fun waitUntilNavButtonClicked() {
        waitUntilSeeNodeCd("Navigation button on top app bar")
        nodeNavButton.performClick()
    }

    /**
     * Waits until joke categories error text, in the navigation drawer,
     * is displayed on screen and asserts that it is displayed.
     */
    fun waitUntilCategoriesErrorTextDisplayed() {
        waitUntilSeeNodeCd("Joke categories error text, in navigation drawer")
        nodeCategoriesErrorText.assertIsDisplayed()
    }

    /**
     * Waits until joke categories error text,
     * in navigation drawer, is the correct text,
     * when network error occurs.
     */
    fun waitUntilCategoriesErrorTextCorrectWhenNetworkError() {
        waitUntilSeeNodeCd("Joke categories error text, in navigation drawer")
        nodeCategoriesErrorText.assertTextEquals("Check your internet connection.")
    }

}