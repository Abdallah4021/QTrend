package com.neu.trend.network

import com.neu.trend.managers.ApplicationManager
import okhttp3.Interceptor
import okhttp3.Response


class AccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return ApplicationManager.getAuthorization()?.let { authorization ->
            val authorisedRequest = originalRequest
                .newBuilder()
                .attachToken(authorization.accessToken)
                .build()
            return chain.proceed(authorisedRequest)
        } ?: chain.proceed(originalRequest)
    }
}