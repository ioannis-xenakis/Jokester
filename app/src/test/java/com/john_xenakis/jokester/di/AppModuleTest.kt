package com.john_xenakis.jokester.di

import com.john_xenakis.jokester.util.Constants.BASE_URL
import org.junit.Test

/**
 * Unit test for App Module.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
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