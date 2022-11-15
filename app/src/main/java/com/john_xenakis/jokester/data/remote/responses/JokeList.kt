package com.john_xenakis.jokester.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * The api response data class,
 * for returning a joke list,
 * directly from the joke api.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@JsonClass(generateAdapter = true)
data class JokeList(
    /**
     * The amount of jokes that will be in each joke list.
     * How many jokes will be in each joke list.
     */
    @Json(name = "amount")
    val amount: Int,

    /**
     * The error boolean. If this boolean is "true", then it indicates
     * there is an error with the api request
     * and return the rest variables with the error response.
     * If it is "false" then theres not an error
     * and will successfully return the api request data(for ex. getting the jokes).
     */
    @Json(name = "error")
    val error: Boolean,

    /**
     * The list that contains the jokes.
     */
    @Json(name = "jokes")
    val jokes: List<Joke>
)