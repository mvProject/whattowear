package com.kinectpro.whattowear.data.model

import com.kinectpro.whattowear.data.model.wear.WearItem

data class TripItem(
    val tripId: String,
    val placeId: String,
    val place: String,
    val startDate: Long,
    val endDate: Long,
    val checkList: List<WearItem>?
)