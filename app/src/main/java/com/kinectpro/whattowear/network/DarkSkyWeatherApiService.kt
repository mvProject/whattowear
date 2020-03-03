package com.kinectpro.whattowear.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kinectpro.whattowear.utils.getRequestSettingsInterceptor
import com.kinectpro.whattowear.utils.getLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DarkSkyWeatherApiService {

    fun initApi(): DarkSkyEndPoint {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor(getRequestSettingsInterceptor())
            .build()
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }
            .build()
            .create(DarkSkyEndPoint::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.darksky.net/"
    }
}