package com.kinectpro.whattowear.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.StyleSpan
import java.util.concurrent.TimeUnit

/**
 * Extension to сreate a spannable string from all the elements of list of dates
 * @return single spanned string value
 */
fun List<Long>.convertToShortDateFormatSpannedString(
    backgroundColor: Int,
    foregroundColor: Int
): SpannedString {
    return this.joinToSpannedString(" ") {
        TimeUnit.SECONDS.toMillis(it).convertDateToReadableFormat(
            STATE_DATE_READABLE_PATTERN
        ).convertToRoundedBackgroundSpannable(backgroundColor, foregroundColor)
    }
}

/**
 * Extension for joining spanned strings
 * @return SpannableStringBuilder
 */
private fun <T> Iterable<T>.joinToSpannedString(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = " ",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)? = null
): SpannedString {
    return SpannedString(
        joinTo(
            SpannableStringBuilder(),
            separator,
            prefix,
            postfix,
            limit,
            truncated,
            transform
        )
    )
}

/**
 * Extension converting any single string to spannable with predefined decoration
 */
fun String.convertToRoundedBackgroundSpannable(
    backgroundColor: Int,
    foregroundColor: Int
): SpannableString {
    val span = SpannableString(this)
    span.setSpan(StyleSpan(Typeface.BOLD), 0, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    span.setSpan(
        RoundedBackgroundSpan(
            backgroundColor,
            foregroundColor,
            BACKGROUND_CORNER_RADIUS,
            HORIZONTAL_PADDING,
            VERTICAL_PADDING
        ), 0, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return span
}