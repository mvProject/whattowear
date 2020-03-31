package com.kinectpro.whattowear.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kinectpro.whattowear.data.model.location.PlaceTrip

/**
 * Return equivalent Json representation for PlaceTrip object
 */
fun PlaceTrip.placeToJson(): String {
    return Gson().toJson(this)
}

/**
 * Return Placetrip from deserialized json
 * Return null if 'this' is null or empty
 */
fun String?.jsonToPlace(): PlaceTrip? {
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, PlaceTrip::class.java)
    }
    return null
}

/**
 * Return equivalent Json representation for List<Long> object
 */
fun List<Long>.dateRangeToJson(): String {
    return Gson().toJson(this)
}

/**
 * Return List<Long> from deserialized json
 * Return null if 'this' is null or empty
 */
fun String?.jsonToDateRange(): List<Long>? {
    val sType = object : TypeToken<List<Long>>() {}.type
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, sType)
    }
    return null
}
