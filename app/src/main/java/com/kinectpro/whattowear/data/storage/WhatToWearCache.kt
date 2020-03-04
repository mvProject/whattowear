package com.kinectpro.whattowear.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.kinectpro.whattowear.data.location.PlaceTrip
import com.kinectpro.whattowear.data.wear.model.item.WearItem
import com.kinectpro.whattowear.utils.*

class WhatToWearCache(context: Context) :
    ICache {

    private val PREFS_NAME = "WhatToWearPrefs"
    private val TRIP_SELECTED_WEAR_KIT = "tripSelectedWearKit"
    private val TRIP_SELECTED_DATA_RANGE = "tripSelectedDataRange"
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

    override fun getLastSelectedDateRange(): List<Long>? {
        val dateRange = sharedPreferences.getString(TRIP_SELECTED_DATA_RANGE, null)
        return dateRange?.jsonToDateRange()
    }

    override fun setLastSelectedDateRange(selectedDates: List<Long>) {
        sharedPreferences.edit()
            .putString(TRIP_SELECTED_DATA_RANGE, selectedDates.dateRangeToJson()).apply()
    }

    override fun getLastSelectedTripKit(): List<WearItem>? {
        val wears = sharedPreferences.getString(TRIP_SELECTED_WEAR_KIT, null)
        return wears?.jsonToKitList()
    }

    override fun setLastSelectedTripKit(selectedKitItems: List<WearItem>) {
        sharedPreferences.edit().putString(TRIP_SELECTED_WEAR_KIT, selectedKitItems.kitListToJson())
            .apply()
    }
}