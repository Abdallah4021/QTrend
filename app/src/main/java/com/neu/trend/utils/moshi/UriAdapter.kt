package com.neu.trend.utils.moshi

import android.net.Uri
import com.squareup.moshi.FromJson


internal class UriAdapter {
    @FromJson
    fun fromJson(uri: String): Uri {
        return Uri.parse(uri)
    }
}