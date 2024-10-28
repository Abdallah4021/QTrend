package com.neu.trend.utils

import android.content.res.Resources
import android.os.Build
import java.util.*

object Utils {
    fun getDeviceLanguage(): String? {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0]
        } else {
            Resources.getSystem().configuration.locale
        }
        return locale.language
    }
}