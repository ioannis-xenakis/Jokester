package com.john_xenakis.jokester.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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