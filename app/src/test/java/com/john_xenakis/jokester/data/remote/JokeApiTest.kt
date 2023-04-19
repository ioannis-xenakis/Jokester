package com.john_xenakis.jokester.data.remote

import com.john_xenakis.jokester.data.remote.responses.Flags
import com.john_xenakis.jokester.data.remote.responses.Joke
import com.john_xenakis.jokester.data.remote.responses.JokeList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
 * Unit test, for testing Joke Api.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class JokeApiTest {

    /**
     * Test if getting the joke list is successful.
     */
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

            coEvery { jokeApi.getJokeList("", jokesAmount, null) } returns jokeList

            val apiJokeListResult = jokeApi.getJokeList("", jokesAmount, null)

            coVerify { jokeApi.getJokeList("", jokesAmount, null) }
            assertEquals(jokeList, apiJokeListResult)
        }
    }
}