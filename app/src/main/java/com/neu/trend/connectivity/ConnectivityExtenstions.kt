package com.neu.trend.connectivity

import com.neu.trend.connectivity.base.ConnectivityProvider


fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
    return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
}