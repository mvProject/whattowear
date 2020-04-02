package com.kinectpro.whattowear.utils

import android.text.format.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit

const val DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS = 1
const val DATE_ERROR_MAX_LENGTH_EXCEEDED = 2
const val START_DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS = 3

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
        startDate <= 0L -> START_DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS
        endDate - startDate > TimeUnit.DAYS.toMillis(DATE_RANGE_MAX_LENGTH_ALLOWED) -> DATE_ERROR_MAX_LENGTH_EXCEEDED
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
    val start = Calendar.getInstance().apply { timeInMillis = startDate }
    val end = Calendar.getInstance().apply { timeInMillis = endDate }
    if (start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH)) {
            return true
    }
    return false
}

/**
 * Method comparing selected start date hour and end date hour
 * @param startDate first date of a range
 * @param endDate last date of a range
 * @return true if start date hour is less or equal end date hour, otherwise return false
 */
fun isStartHourGreaterThenEndHour(startDate: Long, endDate: Long): Boolean {
    val start = Calendar.getInstance().apply { timeInMillis = startDate }
    val end = Calendar.getInstance().apply { timeInMillis = endDate }
    if (start.get(Calendar.HOUR_OF_DAY) <= end.get(Calendar.HOUR_OF_DAY)) {
        return true
    }
    return false
}

/**
 * Method comparing selected start date and end date for both is same date and start is not greater than end
 * @param startDate first date of a range
 * @param endDate last date of a range
 * @return true if both dates are same day and start date hour is less or equal end date hour, otherwise return false
 */
fun isSingleDayHoursProperSelected(startDate: Long, endDate: Long): Boolean {
    return isDaysAreSame(startDate, endDate) && isStartHourGreaterThenEndHour(startDate, endDate)
}