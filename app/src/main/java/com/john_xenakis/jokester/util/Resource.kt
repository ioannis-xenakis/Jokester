package com.john_xenakis.jokester.util

import java.util.*

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
 * The response state(if the response is successful/error).
 * @param data THe data with the according response.
 * @param message The message when theres error, explaining the reason of the error.
 * @param code The code number when theres error with the response.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
sealed class Resource<out T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    /**
     * The successful response with its data.
     * @param data The data of the successful response.
     */
    class Success<T>(data: T): Resource<T>(data)

    /**
     * The error response that isn't successful.
     * @param data The data if it exists for error response.
     * @param message The message of the error explaining the reason.
     * @param code The code number of the error type.
     */
    class Error<T>(data: T? = null, message: String?, code: Int? = null): Resource<T>(data, message, code)

    /**
     * The error response when theres some problem with the network(for ex. no internet connection)
     * @param data The data if it exists for error response.
     * @param message The message of the error explaining the reason.
     */
    class NetworkError<T>(data: T? = null, message: String?): Resource<T>(data, message)

    /**
     * The error response when theres problem with constructing the http request.
     * @param data The data if it exists for error response.
     * @param message The message of the error explaining the reason.
     * @param code The code number of the error type.
     */
    class HttpError<T>(data: T? = null, message: String?, code: Int? = null): Resource<T>(data, message, code)
    class Loading<T>(data: T? = null): Resource<T>(data)

    override fun equals(other: Any?): Boolean {
        return if (other === this || other?.javaClass == this.javaClass) {
            true
        } else if (other == null || other.javaClass != this.javaClass) {
            false
        } else {
            Objects.equals(other, other)
        }
    }

    override fun hashCode(): Int {
        return this.javaClass.hashCode()
    }

}