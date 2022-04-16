package com.john_xenakis.jokester.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.john_xenakis.jokester.MainActivity
import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.di.AppModule
import com.john_xenakis.jokester.repository.JokeRepository
import com.john_xenakis.jokester.repository.JokeRepositoryImpl
import com.john_xenakis.jokester.ui.theme.JokesterTheme
import com.john_xenakis.jokester.webmock.DisconnectDispatcher
import com.john_xenakis.jokester.webmock.ErrorDispatcher
import com.john_xenakis.jokester.webmock.SuccessDispatcher
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import john_xenakis.jokester.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

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
 * Ui test with Jetpack compose, for testing Home Screen.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@UninstallModules(AppModule::class)
@HiltAndroidTest
class HomeScreenTest {

    /**
     * Rule for hilt. Hilt rule should be created first,
     * before any other rules are created on the Home Screen Test class.
     */
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    /**
     * Rule for jetpack compose ui elements.
     */
    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * The joke repository for testing purposes.
     */
    @Inject
    lateinit var testJokeRepository: JokeRepository

    /**
     * The joke api for testing purposes.
     */
    @Inject
    lateinit var testJokeApi: JokeApi

    /**
     * The view model of the home screen, for testing purposes.
     */
    @Inject
    lateinit var testHomeViewModel: HomeViewModel

    /**
     * The mockWebServer variable for using mocked http responses(for ex. using mocked/fake api responses).
     */
    private val mockWebServer by lazy { MockWebServer() }

    /**
     * The @Before init function runs before each test launches/starts.
     */
    @Before
    fun init() {
        hiltRule.inject()
        mockWebServer.start(BuildConfig.PORT)
        launchHomeScreen()
    }

    /**
     * The @After tearDown function runs every time after each test ends.
     */
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * Launches/starts up the home screen and it's content.
     */
    private fun launchHomeScreen() {
        composeTestRule.activity.setContent {
            JokesterTheme {
                HomeScreen()
            }
        }
    }

    /**
     * Test if joke repository is not null(not empty).
     */
    @Test
    fun testJokeRepositoryNotNull() {
        mockWebServer.dispatcher = SuccessDispatcher()
        assertNotNull(testJokeRepository)
    }

    /**
     * Test if joke api is not null(not empty).
     */
    @Test
    fun testJokeApiNotNull() {
        mockWebServer.dispatcher = SuccessDispatcher()
        assertNotNull(testJokeApi)
    }

    /**
     * Test if jokes in joke list on horizontal pager exists.
     */
    @Test
    fun testJokesInJokeListExist() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            displayedHorizontalPager()
            waitUntilJokeDisplayed()
        }

        testHomeViewModel.jokeList.value.forEach { jokeContent ->
            composeTestRule.onNodeWithText(jokeContent.jokeText)
                .assertExists()
            assertNotNull(jokeContent.jokeText)
            assertNotEquals("", jokeContent.jokeText)
        }
    }

    /**
     * Test if joke text is displayed on screen.
     */
    @Test
    fun testJokeTextIsDisplayed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            waitUntilJokeTextDisplayed()
        }
    }

    /**
     * Test if retry box(the container of error text message and retry button)
     * is displayed, when network error occurs(no internet connection).
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRetryBoxIsDisplayedWhenNetworkError() {
        runTest {
            mockWebServer.dispatcher = DisconnectDispatcher()

            homeScreenRobot(composeTestRule) {
                waitUntilErrorDisplayed()
            }
        }
    }

    /**
     * Test if retry box
     * (the container of error text message, error code and retry button)
     * is displayed, when error with code 106 occurs.
     */
    @Test
    fun testRetryBoxIsDisplayedWhenError106() {
        mockWebServer.dispatcher = ErrorDispatcher()

        homeScreenRobot(composeTestRule) {
            waitUntilErrorDisplayed()
        }
    }

    /**
     * Test if error code in retry box(error container),
     * is equal to the correct error code which is 106.
     */
    @Test
    fun testErrorCodeIsCorrect() {
        mockWebServer.dispatcher = ErrorDispatcher()

        homeScreenRobot(composeTestRule) {
            waitUntilErrorCodeEquals()
        }
    }

    /**
     * Test if error message in retry box(error container),
     * is equal to the correct error message.
     */
    @Test
    fun testErrorMessageIsCorrect() {
        mockWebServer.dispatcher = ErrorDispatcher()

        homeScreenRobot(composeTestRule) {
            waitUntilErrorMessageEquals()
        }
    }

    /**
     * Ui test for checking if top app bar,
     * is displayed on screen.
     */
    @Test
    fun testTopAppBarIsDisplayed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            displayedTopBar()
        }
    }

    /**
     * Ui test for checking if title text
     * from top app bar,
     * is visible.
     */
    @Test
    fun testTitleTextOfTopAppBarIsDisplayed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            displayedTopBarTitle()
        }
    }

    /**
     * Ui test for checking if progress indicator,
     * is displayed on screen.
     */
    @Test
    fun testJokeProgressIndicatorIsDisplayed() {
        homeScreenRobot(composeTestRule) {
            displayedProgressIndicator()
        }
    }

    /**
     * Ui test for checking if "previous joke" button,
     * is displayed on screen.
     */
    @Test
    fun testPreviousJokeButtonIsDisplayed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            displayedPreviousJoke()
        }
    }

    /**
     * Ui test for checking if "next joke" button,
     * is displayed on screen.
     */
    @Test
    fun testNextJokeButtonIsDisplayed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        homeScreenRobot(composeTestRule) {
            displayedNextJoke()
        }
    }

    /**
     * The app module with its provide functions,
     * but it is only for testing purposes and only for this Home Screen test class.
     */
    @Module
    @InstallIn(SingletonComponent::class)
    object TestAppModule {

        /**
         * Provides the joke repository to the app.
         * @param jokeApi The joke api.
         * @return The main implementation of joke repository.
         */
        @Provides
        fun provideJokeRepository(
            jokeApi: JokeApi
        ) = JokeRepositoryImpl(jokeApi, Dispatchers.IO)

        /**
         * Provides the joke api to the app and its code.
         * @return The joke api.
         */
        @Provides
        fun provideHomeViewModel(
            jokeRepository: JokeRepository
        ) = HomeViewModel(jokeRepository)

        /**
         * Provides the moshi instance.
         */
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder().build()

        /**
         * Provides the joke api.
         */
        @Provides
        fun provideJokeApi(moshi: Moshi): JokeApi {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("http://127.0.0.1:${BuildConfig.PORT}")
                .build()
                .create(JokeApi::class.java)
        }
    }
}