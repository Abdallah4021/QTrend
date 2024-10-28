package com.neu.trend.data.repository

import com.neu.consumer.domain.model.base.ApiError
import com.neu.trend.data.datasource.RemoteDataSource
import com.neu.trend.domain.repository.UserRepository
import com.neu.trend.network.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class UserRepositoryImp @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    UserRepository {

    override fun getTrendingRepositories(
        query: String,
        onError: (ApiError) -> Unit,
    ) = flow {
        remoteDataSource.getTrendingRepositories(query).handleResponse(this, onError)
    }.flowOn(Dispatchers.IO)

    override fun getTrendingRepositories(
        query: String,
        page: Int,
        onError: (ApiError) -> Unit,
    ) = flow {
        remoteDataSource.getTrendingRepositories(query, page).handleResponse(this, onError)
    }.flowOn(Dispatchers.IO)
}