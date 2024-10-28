package com.neu.trend.domain.model.base

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
    val documentation_url: String,
) {
    fun getErrorMessage(): String = message
}

