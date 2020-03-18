package com.kinectpro.whattowear.network.endpoint

import com.kinectpro.whattowear.data.model.response.DarkSkyWeather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DarkSkyEndPoint {
    @GET("forecast/{key}/{latitude},{longitude},{time}")
    suspend fun getSingleForecastAsync(
        @Path("key") key: String,
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Path("time") time: String,
        @Query("lang") lang: String
    ): DarkSkyWeather

}