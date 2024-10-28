package com.neu.trend.network


enum class NetworkErrorStatusCode(val value: Int) {

    UNKNOWN(0);

    companion object {
        fun fromValue(value: Int) = NetworkErrorStatusCode.values().first { it.value == value }
    }
}