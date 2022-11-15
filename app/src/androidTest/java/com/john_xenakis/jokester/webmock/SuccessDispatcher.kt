package com.john_xenakis.jokester.webmock

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.john_xenakis.jokester.webmock.FileReaderUtil.fileToString
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * The JSON response file name,
 * that is responsible for getting the jokes successfully.
 */
const val GET_JOKES_SUCCESS_FILE = "gets_jokes_successfully.json"

/**
 * The Dispatcher that is used.
 * for returning the successful JSON Mock Response.
 *
 * @constructor The dispatcher needed for testing.
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = fileToString(context, GET_JOKES_SUCCESS_FILE)
        return MockResponse().setResponseCode(200).setBody(responseBody)
    }
}