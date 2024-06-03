package br.com.otakult.interceptor

import br.com.otakult.local.ACCESS_TOKEN
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Test

private const val AUTHORIZATION_HEADER = "Authorization"
private const val ACCEPT_HEADER = "accept"

class AddBasicHeadersInterceptorTest {
    private val interceptor = AddBasicHeadersInterceptor()

    @Test
    fun `WHEN the interceptor is executed THEN should add the expected headers to the request`() {
        val request = Request.Builder()
            .url("https://otakult.com")
            .build()

        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("Ok")
            .build()

        val mockChain = mockk<Interceptor.Chain>(relaxed = true) {
            every { request() } returns request
            every { proceed(any()) } returns response
        }

        interceptor.intercept(mockChain)

        val slot = slot<Request>()
        verify { mockChain.proceed(capture(slot)) }

        assertEquals("application/json", slot.captured.header(ACCEPT_HEADER))
        assertEquals("Bearer $ACCESS_TOKEN", slot.captured.header(AUTHORIZATION_HEADER))
    }
}