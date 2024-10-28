package com.neu.trend.managers

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*

class LocaleContextWrapper constructor(base: Context?) : ContextWrapper(base) {

    companion object {
        fun wrap(context: Context, newLocale: Locale?): LocaleContextWrapper {
            val res = context.resources
            val configuration = res.configuration
            val newContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                context.createConfigurationContext(configuration)
            } else {
                configuration.setLocale(newLocale)
                Locale.setDefault(newLocale!!)
                context.createConfigurationContext(configuration)
            }
            return LocaleContextWrapper(newContext)
        }
    }

}