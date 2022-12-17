package com.john_xenakis.jokester.home

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
     * On all ui elements(nodes) that is found,
     * use nodes with content description named "error code".
     */
    private val allNodesErrorCode by lazy {
        composeTestRule.onAllNodesWithContentDescription("Error code")
    }

    /**
     * On all ui elements(nodes) that is found,
     * use nodes with content description named "error text message".
     */
    private val allNodesErrorMessage by lazy {
        composeTestRule.onAllNodesWithContentDescription("Error text message")
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
     * On all ui elements(nodes) that is found,
     * use nodes with tag named "joke text box 0".
     */
    private val allNodesJokeBox0 by lazy {
        composeTestRule.onAllNodesWithTag("Joke text box 0")
    }

    /**
     * On all ui elements(nodes) that is found,
     * use nodes with tag named "joke text 0".
     */
    private val allNodesJokeText0 by lazy {
        composeTestRule.onAllNodesWithTag("Joke text 0")
    }

    /**
     * On all ui elements(nodes) that is found,
     * use nodes with tag named "error box".
     */
    private val allNodesErrorBox by lazy {
        composeTestRule.onAllNodesWithTag("Error box")
    }

    /**
     * The test on the android device/emulator should wait
     * and not continue(the app keeps running but the test code freezes)
     * until a condition is met(the untilBoolean becomes true)
     * and the test keeps on the rest test code.
     *
     * Also it has a timeout. If the condition isn't met(the untilBoolean)
     * and the waiting time exceeds the timeout, it fails and stops the test.
     */
    private fun waitUntil(untilBoolean: Boolean) {
        composeTestRule.waitUntil(10000L) { untilBoolean }
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
     * Waits until joke box(background box and text) is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilJokeDisplayed() {
        waitUntil(allNodesJokeBox0.fetchSemanticsNodes().size == 1)
        nodeJokeBox0.assertIsDisplayed()
    }

    /**
     * Waits until joke text(text only and not the background box)
     * is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilJokeTextDisplayed() {
        waitUntil(allNodesJokeText0.fetchSemanticsNodes().size == 1)
        nodeJokeText0.assertIsDisplayed()
    }

    /**
     * Waits until error box(error container box with all error info and button), is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorDisplayed() {
        waitUntil(allNodesErrorBox.fetchSemanticsNodes().size == 1)
        nodeErrorBox.assertIsDisplayed()
    }

    /**
     * Waits until error code number(basically a text together with its error code number) is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorCodeEquals() {
        waitUntil(allNodesErrorCode.fetchSemanticsNodes().size == 1)
        nodeErrorCode.assertTextEquals("Error code: 106")
    }

    /**
     * Waits until error message text is seen on screen and then asserts the test that is displayed.
     */
    fun waitUntilErrorMessageEquals() {
        waitUntil(allNodesErrorMessage.fetchSemanticsNodes().size == 1)
        nodeErrorMessage.assertTextEquals("No matching joke found")
    }

}