package com.neu.trend.network

import android.util.Log
import com.neu.consumer.domain.model.base.ApiError
import com.neu.trend.domain.model.base.ErrorResponse
import com.neu.trend.utils.moshi.JsonData
import com.neu.trend.utils.moshi.fromJson
import com.skydoves.sandwich.*
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.flow.FlowCollector
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend inline fun <T> ApiResponse<T>.handleResponse(
    flowCollector: FlowCollector<T>,
    crossinline onError: (ApiError) -> Unit,
) {
    this.suspendOnSuccess {
        data.whatIfNotNull {
            flowCollector.emit(it)
        }
    }.onError {
        try {
            Log.d("errorBody:AAAAAAA AAAA ", "handleResponse: " + response.errorBody());
            val errorBody = response.errorBody()?.charStream()?.readText()
            print("errorBody:AAAAAAA AAAA  $errorBody")
            // Check if the response body is a JSON object or plain text
            val errorResponse = if (errorBody?.startsWith("{") == true) {
                // Parse as JSON object
                val jsonObj = JSONObject(errorBody)
                JsonData(jsonObj.toString()).fromJson<ErrorResponse>()
            } else {
                // If not JSON object, consider it as plain error message
                null
            }

            errorResponse?.let {
                onError(ApiError(it.getErrorMessage(), statusCode.code))
            } ?: run {
                // Use the plain text message or default message
                onError(ApiError(errorBody ?: message(), statusCode.code))
            }
        } catch (e: JSONException) {
            onError(ApiError(message(), statusCode.code))
        }

    }.onException {
        if (this.exception is UnknownHostException || this.exception is SocketTimeoutException) {
            onError(ApiError("No internet connection"))
        } else {
            onError(ApiError(this.exception.localizedMessage ?: "There is a problem"))
        }
    }
}
