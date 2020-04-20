package com.kinectpro.whattowear.data.model.trip

import com.kinectpro.whattowear.data.model.wear.WearItem

data class TripWithWears(
    val trip: TripItem,
    val wears: List<WearItem>
)