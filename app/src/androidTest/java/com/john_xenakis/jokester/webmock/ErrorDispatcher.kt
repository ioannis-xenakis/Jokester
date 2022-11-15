package com.john_xenakis.jokester.webmock

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.john_xenakis.jokester.webmock.FileReaderUtil.fileToString
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * The JSON response file name,
 * that is responsible,
 * for showing a an error with code 106.
 */
const val GET_JOKES_ERROR_106_FILE = "error_code_106.json"

/**
 * The Dispatcher that is used.
 * for returning a JSON Mock Response,
 * that is not successful and returns an error.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class ErrorDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = fileToString(context, GET_JOKES_ERROR_106_FILE)
        return MockResponse().setResponseCode(106).setBody(responseBody)
    }
}