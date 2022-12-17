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