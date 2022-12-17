package com.john_xenakis.jokester.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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