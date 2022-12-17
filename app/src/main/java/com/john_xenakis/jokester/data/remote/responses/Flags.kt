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
 * for returning the flags,
 * directly from the joke api. The flags are used for adding flags to the jokes/filtering them.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@JsonClass(generateAdapter = true)
data class Flags(
    /**
     * The explicit flag boolean for flagging/filtering jokes.
     */
    @Json(name = "explicit")
    val explicit: Boolean,

    /**
     * The nsfw("Not Safe For Work" is a abbreviation
     * for specifying jokes that may be offensive)
     * flag boolean for flagging/filtering jokes.
     */
    @Json(name = "nsfw")
    val nsfw: Boolean,

    /**
     * The political flag boolean(politics related jokes) for flagging/filtering jokes.
     */
    @Json(name = "political")
    val political: Boolean,

    /**
     * The racist flag boolean for flagging/filtering jokes.
     */
    @Json(name = "racist")
    val racist: Boolean,

    /**
     * The religious flag boolean for flagging/filtering jokes.
     */
    @Json(name = "religious")
    val religious: Boolean,

    /**
     * The sexist flag boolean for flagging/filtering jokes.
     */
    @Json(name = "sexist")
    val sexist: Boolean
)