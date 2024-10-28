package com.neu.trend.navigator

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.neu.trend.main.MainActivity


class ScreenNavigator(
    private var activity: Activity,
    private var fragmentManager: FragmentManager,
) {
    fun toMainScreen() {
        MainActivity.start(activity)
    }

}