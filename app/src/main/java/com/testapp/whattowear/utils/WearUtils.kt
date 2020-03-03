package com.testapp.whattowear.utils

import kotlin.collections.mutableListOf

fun getWearsToSelectAccordingTempRange(temps: List<Float>): List<Wears> {
    val allWears = getDummyWears()
    val neededWears = mutableListOf<Wears>()
    for (temp in temps) {
        neededWears.addAll(allWears.filter { it.type == convertTempToWearType(temp) })
    }
    return neededWears.distinct()
}

fun convertTempToWearType(temp: Float): WearType =
    when {
        temp < -10 -> {
            WearType.COLD
        }
        temp > 10 -> {
            WearType.HOT
        }
        else -> WearType.NORMAL
    }


enum class WearType {
    COLD,
    NORMAL,
    HOT
}

data class Wears(val name: String, val type: WearType)


fun getDummyWears(): List<Wears> {
    return mutableListOf<Wears>().apply {
        add(Wears("shirt1", WearType.NORMAL))
        add(Wears("shirt2", WearType.HOT))
        add(Wears("jacket", WearType.COLD))
        add(Wears("t-shirt", WearType.HOT))
        add(Wears("pants", WearType.NORMAL))
        add(Wears("warmpants", WearType.COLD))
        add(Wears("shorts", WearType.HOT))
        add(Wears("hat", WearType.COLD))
        add(Wears("cap", WearType.HOT))
    }
}



