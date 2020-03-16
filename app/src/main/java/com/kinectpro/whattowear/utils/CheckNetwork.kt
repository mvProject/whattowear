package com.kinectpro.whattowear.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import android.util.Log

class CheckNetwork(var context: Context?) {

    private val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = getNetworkCallback()

    fun registerNetworkCallback() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
            } catch (e: Exception) {
                isNetworkConnected = false
            }
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            isNetworkConnected = activeNetwork != null && activeNetwork.isConnected
        }
    }

    private fun getNetworkCallback(): NetworkCallback {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network?) {
                isNetworkConnected = true
                Log.d("Wear", "internet present")
            }

            override fun onLost(network: Network?) {
                isNetworkConnected = false
                Log.d("Wear", "internet not")
            }
        }
    }

    companion object {
        var isNetworkConnected = false
    }

    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}





