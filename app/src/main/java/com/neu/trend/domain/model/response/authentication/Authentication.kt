package com.neu.trend.domain.model.response.authentication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Authentication(
    @Json(name = "id") val userId: Int,
    @Json(name = "refresh") val refreshToken: String,
    @Json(name = "access") val accessToken: String
)