package com.kinectpro.whattowear.data.storage

import com.kinectpro.whattowear.data.model.location.PlaceTrip

interface ICache {
    fun getLastSelectedPlace(): PlaceTrip?
    fun setLastSelectedPlace(selectedPlace: PlaceTrip?)
}