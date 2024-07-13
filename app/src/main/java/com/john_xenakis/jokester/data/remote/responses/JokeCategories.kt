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
 * for returning all joke categories,
 * directly from the joke api.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@JsonClass(generateAdapter = true)
data class JokeCategories(

    /**
     * The list containing all of the joke categories.
     */
    @Json
    val categories: List<String>,

    /**
     * The list containing all of the joke category aliases.
     */
    @Json
    val categoryAliases: List<CategoryAliases>,

    /**
     * The error indicating if the joke categories api request, has failed or is successful.
     */
    @Json
    val error: Boolean,

    /**
     * The timestamp when the api request was made and json returned.
     */
    @Json
    val timestamp: Long
)