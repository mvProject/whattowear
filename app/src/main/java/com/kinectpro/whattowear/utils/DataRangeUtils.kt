package com.kinectpro.whattowear.utils

import android.text.format.DateUtils
import java.util.concurrent.TimeUnit

const val DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS = 1
const val DATE_ERROR_INVALID_RANGE = 2
const val DATE_ERROR_MAX_LENGTH_EXCEEDED = 3

const val DATE_RANGE_MAX_LENGTH_ALLOWED = 30L
/**
 * Method which try to generate list of timestamps from start to end date
 *  @param startDate first date of a range
 *  @param endDate last date of a range
 *  @return list of timestamps if proper dates were selected otherwise null
 */
fun getDataRangeForTrip(startDate: Long?, endDate: Long?): List<Long>? {
    return when (isProperDataRangeSelected(startDate, endDate)) {
        null -> {
            val dataRange = mutableListOf<Long>()
            for (item in startDate!!..endDate!! step DateUtils.DAY_IN_MILLIS)
                dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
            dataRange
        }
        else -> null
    }
}

/**
 *  Method for check selected data range
 *  all dates must be non-null and non-zero
 *  @param startDate first date of a range
 *  @param endDate last date of a range
 *  @return null if range match all conditions otherwise error code
 */
fun isProperDataRangeSelected(startDate: Long?, endDate: Long?): Int? {
    return when {
        (startDate == null) || (endDate == null) -> DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS
        (startDate <= 0L) || (endDate <= 0L) -> DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS
        startDate > endDate -> DATE_ERROR_INVALID_RANGE
        endDate - startDate > TimeUnit.DAYS.toMillis(DATE_RANGE_MAX_LENGTH_ALLOWED) -> DATE_ERROR_MAX_LENGTH_EXCEEDED
        else -> null
    }
}