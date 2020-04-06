package com.kinectpro.whattowear.utils

import android.text.format.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit

const val ERROR_DATE_FIELD_NULL = 1
const val ERROR_DATE_MAX_LENGTH_EXCEEDED = 2
const val ERROR_START_DATE_FIELD_ZERO_OR_LESS = 3
const val ERROR_START_DATE_GREATER = 4


const val DATE_RANGE_MAX_LENGTH_ALLOWED = 30L

const val START_DATE_HOURS_DEFAULT = 0
const val START_DATE_MINUTES_DEFAULT = 0
const val START_DATE_SECONDS_DEFAULT = 1
const val END_DATE_HOURS_DEFAULT = 23
const val END_DATE_MINUTES_DEFAULT = 59
const val END_DATE_SECONDS_DEFAULT = 59

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
        (startDate == null) || (endDate == null) -> ERROR_DATE_FIELD_NULL
        startDate <= 0L -> ERROR_START_DATE_FIELD_ZERO_OR_LESS
        startDate > endDate -> ERROR_START_DATE_GREATER
        endDate - startDate > TimeUnit.DAYS.toMillis(DATE_RANGE_MAX_LENGTH_ALLOWED) -> ERROR_DATE_MAX_LENGTH_EXCEEDED
        else -> null
    }
}

/**
 * Method for check selected start & end dates are same day
 * @param startDate first date of a range
 * @param endDate last date of a range
 * @return true if selected dates are same day, otherwise return false
 */
fun isDaysAreSame(startDate: Long, endDate: Long): Boolean {
    if ((startDate > 0) && (endDate > 0)) {
        val start = Calendar.getInstance().apply { timeInMillis = startDate }
        val end = Calendar.getInstance().apply { timeInMillis = endDate }
        if (start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH)) {
            return true
        }
    }
    return false
}