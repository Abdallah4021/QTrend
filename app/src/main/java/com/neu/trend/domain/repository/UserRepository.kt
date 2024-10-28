package com.neu.trend.domain.repository

import com.neu.consumer.domain.model.base.ApiError
import com.neu.trend.domain.model.response.githubrepo.GitHubResponse

import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getTrendingRepositories(query: String, onError: (ApiError) -> Unit): Flow<GitHubResponse>
    fun getTrendingRepositories(
        query: String,
        page: Int,
        onError: (ApiError) -> Unit,
    ): Flow<GitHubResponse>
}