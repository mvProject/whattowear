package com.testapp.whattowear.utils

import com.testapp.whattowear.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

fun getApiInterceptor(): Interceptor {
    return object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url.newBuilder().
                addQueryParameter("key", BuildConfig.DARKSKY_API_KEY).
                addQueryParameter("units", "auto").
                addQueryParameter("exclude", "currently,flags,hourly,minutely,alerts").
                addQueryParameter("lang", "uk").
                build()
            return chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }
}

fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val logs = HttpLoggingInterceptor()
    logs.level = HttpLoggingInterceptor.Level.BODY
    return logs
}