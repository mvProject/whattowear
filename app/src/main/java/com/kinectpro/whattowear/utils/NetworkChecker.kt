package com.kinectpro.whattowear.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build

class NetworkChecker(var context: Context?) {

    private val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = getNetworkCallback()
    private var isNetworkConnected = false
    private var isCallbackRegistered = false

    fun isInternetConnected(): Boolean {
        return isNetworkConnected
    }

    fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isCallbackRegistered) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
                isCallbackRegistered = true
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            isNetworkConnected =
                networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }

    private fun getNetworkCallback(): NetworkCallback {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network?) {
                isNetworkConnected = true
            }

            override fun onLost(network: Network?) {
                isNetworkConnected = false
            }
        }
    }

    fun unRegisterNetworkCallback() {
        if (isCallbackRegistered) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            isCallbackRegistered = false
        }
    }
}
