package com.testapp.whattowear.network

import com.testapp.whattowear.data.WeatherData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface DarkSkyApi {
    @GET("forecast/")
    fun getSingleForecastAsync(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String,
        @Query("time") time : String) : Deferred<WeatherData>

}