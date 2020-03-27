package com.kinectpro.whattowear.data.storage

import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.wear.WearItem

interface ICache {
    fun getLastSelectedPlace(): PlaceTrip?
    fun setLastSelectedPlace(selectedPlace: PlaceTrip)
}