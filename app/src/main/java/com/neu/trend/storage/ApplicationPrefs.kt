package com.neu.trend.storage

import com.chibatching.kotpref.KotprefModel
import com.neu.trend.domain.model.response.authentication.Authentication
import com.neu.trend.global.AppLanguage
import com.neu.trend.utils.Utils
import com.neu.trend.utils.moshi.JsonData
import com.neu.trend.utils.moshi.fromJson
import com.neu.trend.utils.moshi.toJson


object ApplicationPrefs : KotprefModel() {

    override val kotprefName: String
        get() = "hPassPreference"

    // app language
    var appLanguage by stringPref(default = Utils.getDeviceLanguage() ?: AppLanguage.ENGLISH.value)

    // authorization region
    private var mAuthorization: String? by nullableStringPref()
    var authentication: Authentication?
        get() = mAuthorization?.let { JsonData(it).fromJson() }
        set(value) {
            mAuthorization = value.toJson()
        }

    // user region
    private var mUserEntity: String? by nullableStringPref()


    val isLogin: Boolean
        get() = !(mUserEntity.isNullOrEmpty() || mUserEntity == "null")
}