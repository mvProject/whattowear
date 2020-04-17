package com.kinectpro.whattowear.data.model.trip

data class TripItem(
    val id: Int,
    val placeId: String,
    val place: String,
    val nightTemp: String,
    val dayTemp: String,
    val startDate: Long,
    val endDate: Long,
    val tripId: String
)