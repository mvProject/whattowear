package com.kinectpro.whattowear.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kinectpro.whattowear.data.model.location.PlaceTrip

/**
 * Convert instance of PlaceTrip to json string
 * @return string value
 */
fun PlaceTrip.placeToJson(): String {
    return Gson().toJson(this)
}

/**
 * Convert json string to PlaceTrip instance if json not null
 * @return PlaceTrip object or null
 */
fun String?.jsonToPlace(): PlaceTrip? {
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, PlaceTrip::class.java)
    }
    return null
}

/**
 * Convert list of long values to json string
 * @return string value
 */
fun List<Long>.dateRangeToJson(): String {
    return Gson().toJson(this)
}

/**
 * Convert json string to list of long values if json not null
 * @return list<Long> or null
 */
fun String?.jsonToDateRange(): List<Long>? {
    val sType = object : TypeToken<List<Long>>() {}.type
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, sType)
    }
    return null
}
