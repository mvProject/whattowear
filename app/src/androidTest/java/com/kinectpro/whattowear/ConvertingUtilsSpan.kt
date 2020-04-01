package com.kinectpro.whattowear

import android.graphics.Color
import android.text.style.StyleSpan
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kinectpro.whattowear.utils.RoundedBackgroundSpan
import com.kinectpro.whattowear.utils.convertToRoundedBackgroundSpannable
import com.kinectpro.whattowear.utils.convertToShortDateFormatSpannedString
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertingUtilsSpan {

    private val testString = "12.12"
    private val testStringSpanned = "29.03 30.03 31.03 "
    private val backgroundColorSpan = Color.YELLOW
    private val foregroundColorSpan = Color.BLACK

    private val testListLong = listOf(1585510345L, 1585596745L, 1585683145L)

    @Test
    fun convertToRoundedBackgroundSpannable() {
        val spannedString =
            testString.convertToRoundedBackgroundSpannable(backgroundColorSpan, foregroundColorSpan)
        assertEquals(testString, spannedString.toString())

        val spans = spannedString.getSpans<Any>(0, spannedString.length, Any::class.java)
        assertEquals(2, spans.size.toLong())

        val styleSpan = spans[0] as StyleSpan
        val backgroundSpan = spans[1] as RoundedBackgroundSpan
        assertEquals(0, spannedString.getSpanStart(styleSpan).toLong())
        assertEquals(5, spannedString.getSpanEnd(styleSpan).toLong())
        assertEquals(0, spannedString.getSpanStart(backgroundSpan).toLong())
        assertEquals(5, spannedString.getSpanEnd(backgroundSpan).toLong())
    }

    @Test
    fun convertToShortDateFormatSpannedString() {
        val joinedSpannable = testListLong.convertToShortDateFormatSpannedString(
            backgroundColorSpan,
            foregroundColorSpan
        )
        assertNotNull(joinedSpannable)
        assertEquals(testStringSpanned, joinedSpannable.toString())
        assertEquals(18, joinedSpannable.length)

        val spans = joinedSpannable.getSpans<Any>(0, joinedSpannable.length, Any::class.java)
        assertEquals(6, spans.size.toLong())

        val styleSpan = spans[2] as StyleSpan
        assertEquals(6, joinedSpannable.getSpanStart(styleSpan).toLong())
        assertEquals(11, joinedSpannable.getSpanEnd(styleSpan).toLong())

        val backgroundSpan = spans[5] as RoundedBackgroundSpan
        assertEquals(12, joinedSpannable.getSpanStart(backgroundSpan).toLong())
        assertEquals(17, joinedSpannable.getSpanEnd(backgroundSpan).toLong())
    }
}