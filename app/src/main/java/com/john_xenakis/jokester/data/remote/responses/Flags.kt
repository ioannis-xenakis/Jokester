package com.john_xenakis.jokester.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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