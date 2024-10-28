package com.neu.trend.data.services

import com.neu.trend.domain.model.response.githubrepo.GitHubResponse
import com.neu.trend.network.GET_TRENDING_REPOSITORIES
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*


interface UserService {

    @GET(GET_TRENDING_REPOSITORIES)
    suspend fun getTrendingRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        // TODO : Remove this token from here and add it to the interceptor
        @Header("Authorization") token: String = "token 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",
    ): ApiResponse<GitHubResponse>

}