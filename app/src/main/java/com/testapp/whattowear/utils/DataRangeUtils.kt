package com.testapp.whattowear.utils

import android.text.format.DateUtils
import java.util.concurrent.TimeUnit

fun getDataRangeForTrip(startDate: Long, endDate: Long): List<Long>? {
    if ((startDate > 0) and (endDate > 0) and (endDate >= startDate)) {
        val dataRange = mutableListOf<Long>()
        for (item in startDate..endDate step DateUtils.DAY_IN_MILLIS)
            dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
        return dataRange
    }
    return null
}

fun isProperDataRangeSelected(startDate: Long?, endDate: Long?) : Boolean{
    return when {
        (startDate == null) or (endDate == null) -> false
        (startDate!! <= 0L) or (endDate!! <= 0L) -> false
        startDate>endDate -> false
        else -> true
    }
}