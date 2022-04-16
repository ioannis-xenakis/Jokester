package com.john_xenakis.jokester.data.remote

import com.john_xenakis.jokester.data.remote.responses.JokeList
import retrofit2.http.GET
import retrofit2.http.Query

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