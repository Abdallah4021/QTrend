package com.neu.trend.domain.model.response.authentication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//TODO for refreshing Token
@JsonClass(generateAdapter = true)
class RefreshAuthenticationResponse(
    @Json(name = "refresh") val refreshToken: String,
    @Json(name = "access") val accessToken: String
)