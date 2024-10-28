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
        @Query("order") order: String = "desc"
    ): ApiResponse<GitHubResponse>

}