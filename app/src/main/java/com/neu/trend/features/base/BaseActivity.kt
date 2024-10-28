package com.neu.trend.features.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.neu.trend.connectivity.base.ConnectivityProvider
import com.neu.trend.connectivity.hasInternet
import com.neu.trend.coroutine.MainImmediateCoroutineScope
import com.neu.trend.managers.ApplicationManager
import com.neu.trend.managers.LocaleContextWrapper
import com.neu.trend.navigator.ScreenNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.util.*

private val TAG = BaseActivity::class.java.simpleName

open class BaseActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener,
    CoroutineScope by MainImmediateCoroutineScope(SupervisorJob()) {

    // MARK: Variables
    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }
    val screenNavigator by lazy { ScreenNavigator(this, supportFragmentManager) }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    override fun attachBaseContext(newBase: Context?) {
        val context: Context = LocaleContextWrapper.wrap(
            newBase!!,
            Locale(ApplicationManager.getApplicationLanguage().value)
        )
        super.attachBaseContext(context)
    }

    public fun hasInternetConnection(): Boolean {
        return provider.getNetworkState().hasInternet()
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        when (state) {
            is ConnectivityProvider.NetworkState.NotConnectedState -> {

            }
            is ConnectivityProvider.NetworkState.ConnectedState -> {

            }
        }
    }
}