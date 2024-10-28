package com.neu.trend.features.fav

import androidx.hilt.lifecycle.ViewModelInject
import com.neu.trend.domain.repository.UserRepository
import com.neu.trend.features.base.LiveCoroutinesViewModel

class FavViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
) : LiveCoroutinesViewModel() {

}