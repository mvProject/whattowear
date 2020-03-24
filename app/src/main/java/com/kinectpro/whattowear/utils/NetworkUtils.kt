package com.kinectpro.whattowear.utils

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Interceptor for okhttp request which specified request optional params
 * @return request interceptor object
 */
fun getRequestSettingsInterceptor(): Interceptor {
    return object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url.newBuilder()
                .addQueryParameter("units", "si")
                .addQueryParameter("exclude", "currently,hourly,minutely,alerts")
                .build()
            return chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }
}

/**
 * Interceptor which logs http request and response data
 * @return logging interceptor object
 */
fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val logs = HttpLoggingInterceptor()
    logs.level = HttpLoggingInterceptor.Level.BODY
    return logs
}