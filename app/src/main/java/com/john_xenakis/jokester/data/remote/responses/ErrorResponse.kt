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
 * when the api cannot return the joke and returns an error response,
 * directly from the joke api.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(

    /**
     * Additional and more descriptive info message.
     */
    @Json(name = "additionalInfo")
    val additionalInfo: String,

    /**
     * The list of possible causes of the error.
     */
    @Json(name = "causedBy")
    val causedBy: List<String>,

    /**
     * The http status error code number.
     */
    @Json(name = "code")
    val code: Int,

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
     * If the internalError boolean is "true",
     * then it indicates theres a problem/error from the api/server side
     * and theres nothing the user can do rather
     * than wait for the backend/api developer to fix the issue.
     * If it is "false", then theres something wrong from the user/client side
     * or with constructing the api request
     * (for ex. doesn't exist such joke with the provided filter).
     */
    @Json(name = "internalError")
    val internalError: Boolean,

    /**
     * The error's short message explaining the reason of the error.
     */
    @Json(name = "message")
    val message: String,

    /**
     * The timestamp of when the error came.
     */
    @Json(name = "timestamp")
    val timestamp: Long
)