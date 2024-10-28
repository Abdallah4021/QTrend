package com.neu.trend.data.datasource

import com.neu.trend.data.services.UserService
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val userService: UserService,
) {
    suspend fun getTrendingRepositories(
        query: String,
        sort: String = "stars",
        order: String = "desc",
        page: Int = 1,
    ) = userService.getTrendingRepositories(query, page,sort, order)

    suspend fun getTrendingRepositories(
        query: String,
        page: Int,
    ) = userService.getTrendingRepositories(query, page)

}