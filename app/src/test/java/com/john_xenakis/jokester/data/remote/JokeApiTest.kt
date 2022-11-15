package com.john_xenakis.jokester.data.remote

import com.john_xenakis.jokester.data.remote.responses.Flags
import com.john_xenakis.jokester.data.remote.responses.Joke
import com.john_xenakis.jokester.data.remote.responses.JokeList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit test, for testing Joke Api.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class JokeApiTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Test if getting the joke list is successful.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetJokeListIsSuccessful() {
        runTest {
            val jokes = mutableListOf<Joke>()
            val jokesAmount = 10
            for (jokeNumber in 1..jokesAmount) {
                val testJoke = Joke(
                    category = "Programming",
                    delivery = "Joke delivery $jokeNumber .",
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
                    setup = "This is joke setup $jokeNumber.",
                    type = "type"
                )
                jokes.add(testJoke)
            }
            val jokeApi = mockk<JokeApi>()
            val jokeList = JokeList(jokesAmount, false, jokes)

            coEvery { jokeApi.getJokeList(jokesAmount) } returns jokeList

            val apiJokeListResult = jokeApi.getJokeList(jokesAmount)

            coVerify { jokeApi.getJokeList(jokesAmount) }
            assertEquals(jokeList, apiJokeListResult)
        }
    }
}