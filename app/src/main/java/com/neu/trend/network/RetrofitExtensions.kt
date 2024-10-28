package com.neu.trend.network

import okhttp3.OkHttpClient
import okhttp3.Request


internal const val AUTHORIZATION = "Authorization"
internal const val BEARER = "Bearer"

internal fun client(client: OkHttpClient.Builder.() -> Unit): OkHttpClient {
    val builder = OkHttpClient.Builder()
    client(builder)
    return builder.build()
}

fun Request.Builder.attachToken(token: String): Request.Builder {
    attachHeader(AUTHORIZATION, "$BEARER $token")
    return this
}

fun Request.Builder.attachHeader(name: String, value: String): Request.Builder {
    removeHeader(name)
    addHeader(name, value)
    return this
}

