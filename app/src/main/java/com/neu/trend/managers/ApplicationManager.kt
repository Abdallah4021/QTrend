package com.neu.trend.managers

import com.neu.trend.domain.model.response.authentication.Authentication
import com.neu.trend.global.AppLanguage
import com.neu.trend.storage.ApplicationPrefs


object ApplicationManager {

    private var authentication: Authentication? = null

    init {
        authentication = ApplicationPrefs.authentication
    }
    fun getAuthorization(): Authentication? {
        if (authentication == null) {
            this.authentication = ApplicationPrefs.authentication
        }
        return authentication
    }

    fun setAuthorization(authentication: Authentication) {
        this.authentication = authentication
        ApplicationPrefs.authentication = authentication
    }

    // language region
    fun getApplicationLanguage(): AppLanguage {
        return when (ApplicationPrefs.appLanguage) {
            AppLanguage.ENGLISH.value -> {
                AppLanguage.ENGLISH
            }
            else -> AppLanguage.ENGLISH
        }
    }

}
