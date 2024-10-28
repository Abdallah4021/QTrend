package com.neu.trend.features.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neu.trend.domain.model.response.githubrepo.Repository
import com.neu.trend.domain.repository.UserRepository
import com.neu.trend.features.base.LiveCoroutinesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TrendingRepositoriesViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
) : LiveCoroutinesViewModel() {
    private var currentPage = 0
    private var isLastPage = false
    private var isSearchMode = false


    // Variables
    private val _repositories = MutableLiveData<List<Repository>>() // for inside the viewmodel
    val repositories: LiveData<List<Repository>> // for outside the viewmodel (read-only)
        get() = _repositories   // getter

    private val _isNoInternet = MutableLiveData<Boolean>()
    val isNoInternet: LiveData<Boolean> = _isNoInternet

    fun loadTrendingRepositories(query: String) {
        if (isLastPage) return // Stop further loading if it's the last page

        viewModelScope.launch {
            try {
                userRepository.getTrendingRepositories(query, ++currentPage, onError = {
                    isLoading.postValue(false)
                    apiErrorLiveData.postValue(it)
                }).onStart {
                    isLoading.postValue(true)
                }.collect { response ->
                    isLoading.postValue(false)
                    if (response.items.isEmpty()) {
                        isLastPage = true
                    } else {
                        _repositories.postValue(response.items)
                    }
                }
                _isNoInternet.postValue(false)

            } catch (e: Exception) {
                _isNoInternet.postValue(true)
            }
        }
    }


    // Load more data when user scrolls down
    fun loadMoreRepositories(timeFrame: String) {
        if (isSearchMode) return // Skip loading more if in search mode
        val query = when (timeFrame) {
            "month" -> "created:>${getDate(-1, 'M')}"
            "week" -> "created:>${getDate(-1, 'W')}"
            "day" -> "created:>${getDate(-1, 'D')}"
            else -> ""
        }
        loadTrendingRepositories(query) // Request next page
    }

    private fun getDate(amount: Int, unit: Char): String {
        val calendar = Calendar.getInstance()
        when (unit) {
            'M' -> calendar.add(Calendar.MONTH, amount)
            'W' -> calendar.add(Calendar.WEEK_OF_YEAR, amount)
            'D' -> calendar.add(Calendar.DAY_OF_YEAR, amount)
        }
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(calendar.time)
    }

    fun retryLoadRepositories() {
        loadTrendingRepositories("created:>2023-01-01")
    }


    fun startSearchMode() {
        isSearchMode = true
    }

    fun stopSearchMode() {
        isSearchMode = false
    }
}