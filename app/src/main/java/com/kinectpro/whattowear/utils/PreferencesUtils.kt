package com.kinectpro.whattowear.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kinectpro.whattowear.data.PlaceTrip
import com.kinectpro.whattowear.data.wear.model.WearItem

fun PlaceTrip.placeToJson(): String {
    return Gson().toJson(this)
}

fun String?.jsonToPlace(): PlaceTrip? {
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, PlaceTrip::class.java)
    }
    return null
}

fun List<Long>.dateRangeToJson(): String {
    return Gson().toJson(this)
}

fun String?.jsonToDateRange(): List<Long>? {
    val sType = object : TypeToken<List<Long>>() {}.type
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, sType)
    }
    return null

}

fun List<WearItem>.kitListToJson(): String {
    return Gson().toJson(this)
}

fun String?.jsonToKitList(): List<WearItem>? {
    val kitType = object : TypeToken<List<WearItem>>() {}.type
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, kitType)
    }
    return null

}