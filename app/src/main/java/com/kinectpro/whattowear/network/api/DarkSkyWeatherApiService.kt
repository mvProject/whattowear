package com.kinectpro.whattowear.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kinectpro.whattowear.network.endpoint.DarkSkyEndPoint
import com.kinectpro.whattowear.utils.getLoggingInterceptor
import com.kinectpro.whattowear.utils.getRequestSettingsInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val REQUEST_TIMEOUT = 10L

class DarkSkyWeatherApiService {

    fun initApi(): DarkSkyEndPoint {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getRequestSettingsInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .connectionPool(ConnectionPool(0, 15, TimeUnit.SECONDS))
            .build()
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            client(okHttpClient)
        }
            .build()
            .create(DarkSkyEndPoint::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.darksky.net/"
    }
}