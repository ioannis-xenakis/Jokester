package com.john_xenakis.jokester.webmock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy

/**
 * The Dispatcher that is used.
 * for emulating a disconnected internet,
 * thus returning an empty mocked response.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class DisconnectDispatcher(): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY)
    }
}