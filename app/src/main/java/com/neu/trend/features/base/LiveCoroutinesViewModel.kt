package com.neu.trend.features.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neu.consumer.domain.model.base.ApiError


abstract class LiveCoroutinesViewModel : ViewModel() {

    // Loading and Error LiveData
    val apiErrorLiveData: MutableLiveData<ApiError> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
}