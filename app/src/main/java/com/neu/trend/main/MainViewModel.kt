package com.neu.trend.main


import androidx.hilt.lifecycle.ViewModelInject

import com.neu.trend.domain.repository.UserRepository
import com.neu.trend.features.base.LiveCoroutinesViewModel


class MainViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : LiveCoroutinesViewModel() {

}