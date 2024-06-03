package br.com.otakult.interceptor

import br.com.otakult.local.ACCESS_TOKEN
import okhttp3.Interceptor

private const val AUTHORIZATION_HEADER = "Authorization"
private const val ACCEPT_HEADER = "accept"

class AddBasicHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "Bearer $ACCESS_TOKEN")
            addHeader(ACCEPT_HEADER, "application/json")
        }.build()
    )
}