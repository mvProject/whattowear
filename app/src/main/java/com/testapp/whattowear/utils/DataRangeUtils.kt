package com.testapp.whattowear.utils

import android.text.format.DateUtils
import java.util.concurrent.TimeUnit

/**
 * Method which try to generate list of timestamps from start to end date
 *  @param startDate first date of a range
 *  @param endDate last date of a range
 *  @return list of timestamps otherwise false
 */
fun getDataRangeForTrip(startDate: Long?, endDate: Long?): List<Long>? {
    return when (isProperDataRangeSelected(startDate, endDate)) {
        false -> null
        true -> {
            val dataRange = mutableListOf<Long>()
            for (item in startDate!!..endDate!! step DateUtils.DAY_IN_MILLIS)
                dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
            dataRange
        }
    }
}

/**
 *  Method for check selected data range
 *  all dates must be non-null and non-zero
 *  @param startDate first date of a range
 *  @param endDate last date of a range
 *  @return true if range match all conditions otherwise false
 */
fun isProperDataRangeSelected(startDate: Long?, endDate: Long?): Boolean {
    return when {
        (startDate == null) or (endDate == null) -> false
        (startDate!! <= 0L) or (endDate!! <= 0L) -> false
        startDate > endDate -> false
        else -> true
    }
}