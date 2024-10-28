package com.neu.trend.managers

import android.content.Context
import com.bumptech.glide.RequestManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent


object GlideInstance {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface GlideEntryPoint {
        fun requestManager(): RequestManager
    }

    fun getGlide(viewContext: Context): RequestManager {
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(viewContext, GlideEntryPoint::class.java)
        return hiltEntryPoint.requestManager()
    }

}