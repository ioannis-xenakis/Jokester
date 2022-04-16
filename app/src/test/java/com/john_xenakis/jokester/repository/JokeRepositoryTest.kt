package com.john_xenakis.jokester.repository

import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.data.remote.responses.Flags
import com.john_xenakis.jokester.data.remote.responses.Joke
import com.john_xenakis.jokester.data.remote.responses.JokeList
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
 * @version 1.0.0-alpha
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
     * @param jokesAmount How many jokes to make in a single list.
     * @return A list of jokes.
     */
    private fun createJokes(jokesAmount: Int): MutableList<Joke> {
        val jokes = mutableListOf<Joke>()
        for (jokeNumber in 1..jokesAmount) {
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
     * Test if getting the joke list,
     * can be successful with the use of mocks and mockk.io dependency.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListIsSuccessful() {
        runTest {
            val jokesAmount = 10
            val jokes = createJokes(jokesAmount)
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
            val jokes = createJokes(10)
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
            val jokes = createJokes(jokesAmount)
            val jokeList = JokeList(jokesAmount, false, jokes)
            coEvery {
                jokeApi.getJokeList(jokesAmount) } returns jokeList

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
}