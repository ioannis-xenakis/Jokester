package com.john_xenakis.jokester.data.remote

import com.john_xenakis.jokester.data.remote.responses.FlagsList
import com.john_xenakis.jokester.data.remote.responses.JokeCategories
import com.john_xenakis.jokester.data.remote.responses.JokeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

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
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
interface JokeApi {

    /**
     * Gets the jokes in a joke list from the joke api.
     * @param category The specified joke category, to get the jokes from.
     * @param amount The amount number of jokes,
     * @param blacklistFlags The blacklist flags list for filtering joke list.
     * or how many jokes it should get, from the joke api.
     * @param safeMode The mode for filtering unsafe jokes and showing only safe ones.
     * @return The joke list with its jokes.
     */
    @GET("joke/{category}")
    suspend fun getJokeList(
        @Path("category") category: String,
        @Query("amount") amount: Int,
        @Query("blacklistFlags") blacklistFlags: String? = null,
        @QueryName safeMode: String? = null
    ): JokeList

    /**
     * Gets all the joke categories, from the joke api.
     * @return The joke categories.
     */
    @GET("categories")
    suspend fun getJokeCategories(): JokeCategories

    /**
     * Gets the joke flags list, from the joke api.
     * @return The joke flags list.
     */
    @GET("flags")
    suspend fun getJokeFlagsList(): FlagsList
}