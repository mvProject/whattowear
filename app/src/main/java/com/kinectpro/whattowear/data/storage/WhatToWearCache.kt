package com.kinectpro.whattowear.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.utils.*

class WhatToWearCache(context: Context) :
    ICache {

    private val PREFS_NAME = "WhatToWearPrefs"
    private val TRIP_SELECTED_PLACE = "tripSelectedPlace"

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun getLastSelectedPlace(): PlaceTrip? {
        val place = sharedPreferences.getString(TRIP_SELECTED_PLACE, null)
        return place?.jsonToPlace()
    }

    override fun setLastSelectedPlace(selectedPlace: PlaceTrip) {
        sharedPreferences.edit().putString(TRIP_SELECTED_PLACE, selectedPlace.placeToJson()).apply()
    }
}