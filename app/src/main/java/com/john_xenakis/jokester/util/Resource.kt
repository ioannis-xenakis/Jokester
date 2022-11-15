package com.john_xenakis.jokester.util

import java.util.*

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