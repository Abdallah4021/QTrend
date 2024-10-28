package com.neu.trend.features.trending.model

import com.neu.trend.domain.model.response.githubrepo.Repository

data class FavoriteRepositoryEvent(val repository: Repository, val isFavorite: Boolean)