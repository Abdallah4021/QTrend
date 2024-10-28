package com.neu.trend.domain.model.response.githubrepo

import com.squareup.moshi.Json
import java.io.Serializable

data class GitHubResponse(
    @Json(name = "items") val items: List<Repository>,
) : Serializable

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val stargazers_count: Int,
    val language: String?,
    val forks: Int,
    val created_at: String,
    val html_url: String,
    val owner: Owner,
) : Serializable

data class Owner(
    val login: String,
    val avatar_url: String?,
) : Serializable