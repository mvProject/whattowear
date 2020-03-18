package com.kinectpro.whattowear.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build

class CheckNetwork(var context: Context?) {

    private val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = getNetworkCallback()
    private var isNetworkConnected = false

    fun isInternetConnected(): Boolean {
        return isNetworkConnected
    }

    fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            isNetworkConnected = activeNetwork != null && activeNetwork.isConnected
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

    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}





