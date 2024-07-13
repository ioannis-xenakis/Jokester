package com.john_xenakis.jokester.di

import com.john_xenakis.jokester.util.Constants.BASE_URL
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
 * Unit test for App Module.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
class AppModuleTest {

    /**
     * Test if Joke Api's base url is correctly passed.
     */
    @Test
    fun testJokeApiBaseUrlIsCorrect() {
        val jokeApiUrl = AppModule.retrofit.baseUrl().toUrl().toString()
        val jokeApiUrlExpected = BASE_URL.plus("/")
        assert(jokeApiUrl == jokeApiUrlExpected)
    }
}