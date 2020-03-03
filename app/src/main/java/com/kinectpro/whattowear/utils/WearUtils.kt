package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.wear.model.WearItem
import com.kinectpro.whattowear.data.wear.model.WeatherTemp
import kotlin.collections.mutableListOf

fun getWearsToSelectAccordingTempRange(temps: List<Float>): List<WearItem> {
    val allWears = getDummyWears()
    val neededWears = mutableListOf<WearItem>()
    for (temp in temps) {
        neededWears.addAll(allWears.filter { it.temp == convertTempToWearType(temp) })
    }
    return neededWears.distinct()
}

fun convertTempToWearType(temp: Float): WeatherTemp =
    when {
        temp < -10 -> {
            WeatherTemp.COLD
        }
        temp > 10 -> {
            WeatherTemp.HOT
        }
        else -> WeatherTemp.NORMAL
    }

fun getDummyWears(): List<WearItem> {
    return mutableListOf<WearItem>().apply {
        add(WearItem("shirt1", WeatherTemp.NORMAL))
        add(WearItem("shirt2", WeatherTemp.HOT))
        add(WearItem("jacket", WeatherTemp.COLD))
        add(WearItem("t-shirt", WeatherTemp.HOT))
        add(WearItem("pants", WeatherTemp.NORMAL))
        add(WearItem("warmpants", WeatherTemp.COLD))
        add(WearItem("shorts", WeatherTemp.HOT))
        add(WearItem("hat", WeatherTemp.COLD))
        add(WearItem("cap", WeatherTemp.HOT))
    }
}



