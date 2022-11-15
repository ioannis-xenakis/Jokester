package com.john_xenakis.jokester.repository

import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.data.remote.responses.ErrorResponse
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

/**
 * The jokes repository is used for communicating from the api, back to the app.
 * Provides api data access to the app.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
interface JokeRepository {
    /**
     * Gets the joke list.
     */
    suspend fun getJokeList(): Resource<JokeList>
}

/**
 * The main implementation of the joke repository.
 * @param jokeApi The joke api that is used for using the jokes.
 * @param dispatcher The coroutine dispatcher.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@ActivityScoped
class JokeRepositoryImpl @Inject constructor(
    private val jokeApi: JokeApi,
    private val dispatcher: CoroutineDispatcher,
): JokeRepository {
    override suspend fun getJokeList(): Resource<JokeList> {
        return getJokeListApiCall(
            dispatcher = dispatcher
        ) {
            jokeApi.getJokeList(
                PAGE_SIZE
            )
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