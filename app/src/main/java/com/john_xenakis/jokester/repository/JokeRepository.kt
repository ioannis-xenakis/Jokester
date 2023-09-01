package com.john_xenakis.jokester.repository

import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.data.remote.responses.ErrorResponse
import com.john_xenakis.jokester.data.remote.responses.FlagsList
import com.john_xenakis.jokester.data.remote.responses.JokeCategories
import com.john_xenakis.jokester.data.remote.responses.JokeList
import com.john_xenakis.jokester.util.Constants.PAGE_SIZE
import com.john_xenakis.jokester.util.Resource
import com.squareup.moshi.Moshi
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
 * The jokes repository is used for communicating from the api, back to the app.
 * Provides api data access to the app.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
interface JokeRepository {
    /**
     * Gets the joke list.
     * @param jokeCategory The joke category that the jokes gets from.
     * @param flags The blacklist flags in a string, for filtering joke list.
     * @param safeMode THe mode for showing only safe jokes and appropriate for anyone.
     * @return The joke list with its resource class.
     */
    suspend fun getJokeList(jokeCategory: String, flags: String?, safeMode: String?): Resource<JokeList>

    /**
     * Gets all of the joke categories.
     * @return The joke categories with its resource class.
     */
    suspend fun getJokeCategories(): Resource<JokeCategories>

    /**
     * Gets the joke flags list.
     * @return The joke flags list.
     */
    suspend fun getJokeFlagsList(): Resource<FlagsList>
}

/**
 * The main implementation of the joke repository.
 * @param jokeApi The joke api that is used for using the jokes.
 * @param dispatcher The coroutine dispatcher.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@ActivityScoped
class JokeRepositoryImpl @Inject constructor(
    private val jokeApi: JokeApi,
    private val dispatcher: CoroutineDispatcher,
): JokeRepository {
    override suspend fun getJokeList(jokeCategory: String, flags: String?, safeMode: String?): Resource<JokeList> {
        return getJokeListApiCall(
            dispatcher = dispatcher
        ) {
            jokeApi.getJokeList(
                category = jokeCategory,
                amount = PAGE_SIZE,
                blacklistFlags = flags,
                safeMode = safeMode
            )
        }
    }

    override suspend fun getJokeCategories(): Resource<JokeCategories> {
        return getJokeCategoriesApiCall(
            dispatcher = dispatcher
        ) {
            jokeApi.getJokeCategories()
        }
    }

    override suspend fun getJokeFlagsList(): Resource<FlagsList> {
        return getJokeFlagsListApiCall(
            dispatcher = dispatcher
        ) {
            jokeApi.getJokeFlagsList()
        }
    }
}

/**
 * The api call for getting the joke list.
 * @param dispatcher The coroutine dispatcher.
 * @param apiCall The lambda block for returning/getting the joke list.
 * @return The joke list with its resource class.
 */
suspend fun getJokeListApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> JokeList): Resource<JokeList> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.NetworkError(
                    message = "No internet connection."
                )
                is HttpException -> {
                    val convertedErrorBody = convertErrorBody(throwable)
                    Resource.HttpError(
                        message = convertedErrorBody?.message,
                        code = convertedErrorBody?.code
                    )
                }
                else -> Resource.Error(
                    message = throwable.localizedMessage,
                    code = null
                )
            }
        }
    }
}

/**
 * The api call for getting the joke categories.
 * @param dispatcher The coroutine dispatcher.
 * @param apiCall The block for getting/returning the joke categories.
 * @return The joke categories with its resource class.
 */
suspend fun getJokeCategoriesApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> JokeCategories
): Resource<JokeCategories> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when(throwable) {
                is IOException -> Resource.NetworkError(
                    message = "Check your internet connection."
                )
                is HttpException -> {
                    val convertedErrorBody = convertErrorBody(throwable)
                    Resource.HttpError(
                        message = convertedErrorBody?.message,
                        code = convertedErrorBody?.code
                    )
                }
                else -> Resource.Error(
                    message = throwable.localizedMessage,
                    code = null
                )
            }
        }
    }
}

/**
 * The api call for getting the joke flags list.
 * @param dispatcher The coroutine dispatcher.
 * @param apiCall The block for getting/returning the joke flags.
 * @return The joke flags list with its resource class.
 */
suspend fun getJokeFlagsListApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> FlagsList
): Resource<FlagsList> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when(throwable) {
                is IOException -> Resource.NetworkError(
                    message = "Check your internet connection."
                )
                is HttpException -> {
                    val convertedErrorBody = convertErrorBody(throwable)
                    Resource.HttpError(
                        message = convertedErrorBody?.message,
                        code = convertedErrorBody?.code
                    )
                }
                else -> Resource.Error(
                    message = throwable.localizedMessage,
                    code = null
                )
            }
        }
    }
}

/**
 * Converts/gets the error body that is from the api, into an Error Response.
 * @param throwable The throwable that is an Http Exception.
 * @return The converted Error Response.
 * @throws Exception An exception that returns null if thrown.
 */
private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}