package com.neu.trend.utils.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


val moshi = Moshi.Builder()
    .add(SerializeNulls.JSON_ADAPTER_FACTORY)
    .add(KotlinJsonAdapterFactory())
    .add(UriAdapter())
    .build() as Moshi

inline class JsonData(val value: String)

inline fun <reified T> JsonData.fromJson(): T? {
    val adapter = moshi.adapter(T::class.java)
    return adapter.fromJson(this.value)
}

inline fun <reified T> T.toJson(): String {
    return moshi.adapter(T::class.java).toJson(this)
}


inline fun <reified T> JsonData.jsonToList(): List<T>? {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    return moshi.adapter<List<T>>(type).fromJson(this.value)

}


