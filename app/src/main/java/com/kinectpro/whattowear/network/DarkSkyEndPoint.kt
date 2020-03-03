package com.kinectpro.whattowear.network

import com.kinectpro.whattowear.data.DarkSkyWeather
import retrofit2.http.GET
import retrofit2.http.Path

interface DarkSkyEndPoint {
    @GET("forecast/{key}/{latitude},{longitude},{time}")
    suspend fun getSingleForecastAsync(
        @Path("key") key: String,
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Path("time") time: String
    ): DarkSkyWeather

}