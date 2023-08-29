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
 * for returning all aliases for each joke category,
 * directly from the joke api.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@JsonClass(generateAdapter = true)
data class CategoryAliases(
    /**
     * The alias that can be used instead of the default category name.
     */
    @Json
    val alias: String,

    /**
     * The default category name that is used by default and is equal/resolved to the corresponding alias.
     */
    @Json
    val resolved: String
)