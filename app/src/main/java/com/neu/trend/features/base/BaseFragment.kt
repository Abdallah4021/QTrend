package com.neu.trend.features.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes),
    CoroutineScope by CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) {
}