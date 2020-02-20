package com.testapp.whattowear.network

import com.testapp.whattowear.data.DarkSkyWeather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface DarkSkyApi {
    @GET("forecast/{key}/{latitude},{longitude},{time}")
    fun getSingleForecastAsync(
        @Path("key") key : String,
        @Path("latitude") latitude : String,
        @Path("longitude") longitude : String,
        @Path("time") time : String) : Deferred<DarkSkyWeather>

}