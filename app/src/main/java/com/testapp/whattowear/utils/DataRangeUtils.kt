package com.testapp.whattowear.utils

import android.text.format.DateUtils
import java.util.concurrent.TimeUnit

fun getTripDataRange(startDate: Long, endDate: Long): List<Long>? {
    if ((startDate > 0) and (endDate > 0) and (endDate >= startDate)) {
        val dataRange = mutableListOf<Long>()
        for (item in startDate..endDate step DateUtils.DAY_IN_MILLIS)
            dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
        return dataRange
    }
    return null
}