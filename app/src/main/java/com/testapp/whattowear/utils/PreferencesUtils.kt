package com.testapp.whattowear.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.testapp.whattowear.data.ITripKitItem
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.item.model.TripItem

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

fun List<ITripKitItem>.kitListToJson(): String {
    return Gson().toJson(this)
}

fun String?.jsonToKitList(): List<TripItem>? {
    val kitType = object : TypeToken<List<TripItem>>() {}.type
    if (!this.isNullOrEmpty()) {
        return Gson().fromJson(this, kitType)
    }
    return null

}