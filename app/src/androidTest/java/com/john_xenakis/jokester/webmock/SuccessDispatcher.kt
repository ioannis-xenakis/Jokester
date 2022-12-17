package com.john_xenakis.jokester.webmock

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.john_xenakis.jokester.webmock.FileReaderUtil.fileToString
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

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