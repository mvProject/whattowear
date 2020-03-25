package com.kinectpro.whattowear.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

class NetworkChecker(context: Context?) {

    private val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = getNetworkCallback()
    private var isNetworkConnected = false
    private var isCallbackRegistered = false

    private val networkRequest = NetworkRequest.Builder()

    init {
        registerNetworkCallback()
    }

    fun isInternetConnected(): Boolean {
        return isNetworkConnected
    }

    private fun registerNetworkCallback() {
        if (!isCallbackRegistered) {
            connectivityManager.registerNetworkCallback(networkRequest.build(), networkCallback)
            isCallbackRegistered = true
        }
    }

    private fun getNetworkCallback(): NetworkCallback {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network?) {
                isNetworkConnected = connectivityManager.bindProcessToNetwork(network)
            }

            override fun onLost(network: Network?) {
                isNetworkConnected = false
                connectivityManager.bindProcessToNetwork(null)
            }
        }
    }

    fun unregisterNetworkCallback() {
        if (isCallbackRegistered) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            isCallbackRegistered = false
        }
    }
}

