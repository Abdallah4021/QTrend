package com.neu.consumer.domain.model.base

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiError(val message: String, val code: Int? = null)