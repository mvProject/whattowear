package com.kinectpro.whattowear.data.model.trip

import com.kinectpro.whattowear.data.model.wear.WearItem
import java.io.Serializable

data class TripItem(
    val tripId: String,
    val placeId: String,
    val place: String,
    val nightTemp: String,
    val dayTemp: String,
    val startDate: Long,
    val endDate: Long,
    val checkList: List<WearItem>?
) : Serializable