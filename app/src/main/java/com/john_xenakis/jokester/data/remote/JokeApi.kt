package com.john_xenakis.jokester.data.remote

import com.john_xenakis.jokester.data.remote.responses.JokeList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The joke api interface with its api requests.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
interface JokeApi {

    /**
     * Gets the jokes in a joke list from the joke api.
     * @param amount The amount number of jokes,
     * or how many jokes it should get, from the joke api.
     * @return The joke list with its jokes.
     */
    @GET("joke/Any")
    suspend fun getJokeList(
        @Query("amount") amount: Int
    ): JokeList
}