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
 * for returning a joke,
 * directly from the joke api.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@JsonClass(generateAdapter = true)
data class Joke(
    /**
     * The category that the joke is in
     * (for ex. "Programming").
     */
    @Json(name = "category")
    val category: String,

    /**
     * When the jokes type is twopart,
     * the jokes text is divided into 2 texts.
     * One of those texts is the delivery.
     * The first part of the twopart joke.
     */
    @Json(name = "delivery")
    val delivery: String? = null,

    /**
     * The flags that is added in the jokes,
     * for the jokes to be filtered.
     */
    @Json(name = "flags")
    val flags: Flags,

    /**
     * The id number of the joke. Every joke has a unique id number.
     */
    @Json(name = "id")
    val id: Int,

    /**
     * When the jokes type is "single",
     * the joke has a single joke text instead of 2 parts.
     * That one part is this "joke" text.
     */
    @Json(name = "joke")
    val joke: String? = null,

    /**
     * The language that the joke text is written of.
     */
    @Json(name = "lang")
    val lang: String,

    /**
     * When this "safe" boolean is "true",
     * the joke is not offensive in any way
     * and is safe for any user. When the boolean is "false"
     * it could be offensive for some users.
     */
    @Json(name = "safe")
    val safe: Boolean,

    /**
     * When the jokes type is "twopart",
     * the jokes text is divided into 2 texts.
     * One of those texts is the setup.
     * The second part of the twopart joke.
     */
    @Json(name = "setup")
    val setup: String? = null,

    /**
     * The jokes type. If the jokes type,
     * is "twopart", the jokes text divided into 2 parts. "Delivery" and "setup".
     * If it is "single", the jokes text is just one text. "Joke".
     */
    @Json(name = "type")
    val type: String
)