package com.john_xenakis.jokester.repository

import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.data.remote.responses.*
import com.john_xenakis.jokester.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

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
 * Unit test with Mockk.io for mocking, for testing Joke Repository.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
class JokeRepositoryTest {

    /**
     * The mocked Joke Api that imitates getting the jokes from Joke Api.
     */
    private val jokeApi = mockk<JokeApi>()

    /**
     * The mocked Joke Repository that imitates the ready and working mechanism that gets the jokes,
     * from Joke Api.
     */
    private val jokeRepository = mockk<JokeRepository>()

    /**
     * The test dispatcher needed for unit testing.
     * Kotlin coroutines uses dispatchers,
     * for determining which threads should be used for coroutine execution.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    /**
     * Create new jokes, in a joke list.
     * @return A list of jokes.
     */
    private fun createJokes(): MutableList<Joke> {
        val jokes = mutableListOf<Joke>()
        for (jokeNumber in 1..10) {
            val testJoke = Joke(
                category = "Programming", delivery = "Joke delivery $jokeNumber .",
                flags = Flags(
                    explicit = false,
                    nsfw = false,
                    political = false,
                    racist = false,
                    religious = false,
                    sexist = false
                ),
                id = jokeNumber,
                joke = "",
                lang = "en",
                safe = false,
                setup = "This is joke setup $jokeNumber .",
                type = "type"
            )
            jokes.add(testJoke)
        }
        return jokes
    }

    /**
     * Create the joke categories list, as a list of strings.
     * @return The joke categories list, as a list of strings.
     */
    private fun createJokeCategoriesList(): List<String> {
        return listOf(
            "Any",
            "Misc",
            "Programming",
            "Dark",
            "Pun",
            "Spooky",
            "Christmas"
        )
    }

    /**
     * Create the joke category aliases list.
     * @return The list of joke category aliases.
     */
    private fun createJokeCategoryAliases(): List<CategoryAliases> {
        return listOf(
            CategoryAliases(
                "Miscellaneous",
                "Misc"
            ),
            CategoryAliases(
                "Coding",
                "Programming"
            ),
            CategoryAliases(
                "Development",
                "Programming"
            ),
            CategoryAliases(
                "Halloween",
                "Spooky"
            )
        )
    }

    /**
     * Create the joke categories.
     * @return The joke categories.
     */
    private fun createJokeCategories(): JokeCategories {
        return JokeCategories(
            createJokeCategoriesList(),
            createJokeCategoryAliases(),
            false,
            1676652244350L
        )
    }

    /**
     * Test if getting the joke list,
     * can be successful with the use of mocks and mockk.io dependency.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListIsSuccessful() {
        runTest {
            val jokesAmount = 10
            val jokes = createJokes()
            val jokeList = JokeList(jokesAmount, false, jokes)
            coEvery { jokeApi.getJokeList(jokesAmount) } returns jokeList

            val apiJokeListResult = jokeApi.getJokeList(jokesAmount)

            coVerify { jokeApi.getJokeList(jokesAmount) }
            assertEquals(jokeList, apiJokeListResult)

            val resourceSuccess = Resource.Success(apiJokeListResult)
            coEvery { jokeRepository.getJokeList() } returns resourceSuccess

            val repoJokeListResult = jokeRepository.getJokeList()

            coVerify { jokeRepository.getJokeList() }
            assertEquals(resourceSuccess, repoJokeListResult)
        }
    }

    /**
     * Test if getting the joke list from api call (getJokeListApiCall() function),
     * returns successfully the data(jokes), the alternative way without mocks and mockk.io dependency.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListReturnsSuccessfulNoMocks(){
        runTest {
            val jokes = createJokes()
            val jokeList = JokeList(10, false, jokes)
            val apiCallResult = getJokeListApiCall(testDispatcher) { jokeList }
            assertEquals(Resource.Success(getJokeListApiCall(testDispatcher) { jokeList }), apiCallResult)
        }
    }

    /**
     * Test if getting the joke list can return a "Network Error".
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListReturnsNetworkError() {
        runTest {
            val jokesAmount = 10
            val jokes = createJokes()
            val jokeList = JokeList(jokesAmount, false, jokes)
            coEvery {
                jokeApi.getJokeList(jokesAmount)
            } returns jokeList

            val apiJokeListResult = jokeApi.getJokeList(jokesAmount)

            coVerify { jokeApi.getJokeList(jokesAmount) }
            assertEquals(jokeList, apiJokeListResult)

            val resourceNetworkError = Resource.NetworkError(
                data = apiJokeListResult,
                message = "No internet connection."
            )

            coEvery {
                jokeRepository.getJokeList()
            } returns resourceNetworkError

            val jokeRepositoryResult = jokeRepository.getJokeList()

            coVerify {
                jokeRepository.getJokeList()
            }

            assertEquals(
                resourceNetworkError,
                jokeRepositoryResult
            )
        }
    }

    /**
     * Test if getting joke list throws an IOException thus returning Network Error at the end.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListIsIOException() {
        runTest {
            val result = getJokeListApiCall(testDispatcher) { throw IOException() }

            assertEquals(Resource.NetworkError<Any>(message = "No internet connection."), result)
        }
    }

    /**
     * Test if getting the joke categories,
     * can be successful, with the use of mocks and mockk.io dependency.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeCategoriesIsSuccessful() {
        runTest {
            coEvery {
                jokeApi.getJokeCategories()
            } returns createJokeCategories()

            coEvery {
                jokeRepository.getJokeCategories()
            } returns Resource.Success(jokeApi.getJokeCategories())

            assertEquals(
                Resource.Success(
                    jokeApi.getJokeCategories()
                ),
                jokeRepository.getJokeCategories()
            )
            coVerify { jokeRepository.getJokeCategories() }
        }
    }

    /**
     * Test if getting the joke categories can return a network error.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeCategoriesReturnsNetworkError() {
        runTest {
            coEvery {
                jokeRepository.getJokeCategories()
            } returns Resource.NetworkError(
                data = null,
                message = "Check your internet connection."
            )

            assertEquals(
                Resource.NetworkError(
                    data = null,
                    message = "Check your internet connection."
                ),
                jokeRepository.getJokeCategories()
            )
            coVerify { jokeRepository.getJokeCategories() }
        }
    }

    /**
     * Test if getting joke categories can fail resulting in a Http Error.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeCategoriesReturnsHttpError() {
        runTest {
            coEvery {
                jokeRepository.getJokeCategories()
            } returns Resource.HttpError(
                message = "Too many requests.",
                code = 429
            )

            assertEquals(
                Resource.HttpError<JokeCategories>(
                    message = "Too many requests.",
                    code = 429
                ),
                jokeRepository.getJokeCategories()
            )
            coVerify { jokeRepository.getJokeCategories() }
        }
    }

    /**
     * Test if getting joke categories can fail resulting in an Error.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeCategoriesReturnsError() {
        runTest {
            coEvery {
                jokeRepository.getJokeCategories()
            } returns Resource.Error(message = "This is an error message.")

            assertEquals(
                Resource.Error<JokeCategories>(
                    message = "This is an error message."
                ),
                jokeRepository.getJokeCategories()
            )
            coVerify { jokeRepository.getJokeCategories() }
        }
    }
}