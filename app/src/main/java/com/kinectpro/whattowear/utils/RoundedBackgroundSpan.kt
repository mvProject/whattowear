package com.kinectpro.whattowear.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.RectF
import android.text.style.ReplacementSpan
import kotlin.math.roundToInt

class RoundedBackgroundSpan(
    private var backgroundColor: Int,
    private var textColor: Int,
    private var cornerRadius: Int = 8,
    private var horizontalPadding: Int = 0,
    private var verticalPadding: Int = 0
) : ReplacementSpan() {

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val rect = RectF(
            x,
            top + verticalPadding.toFloat(),
            x + horizontalPadding + measureText(paint, text, start, end) + horizontalPadding,
            bottom + verticalPadding.toFloat()
        )
        paint.color = backgroundColor
        canvas.drawRoundRect(rect, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
        paint.color = textColor
        canvas.drawText(text, start, end, x + horizontalPadding, y.toFloat(), paint)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        return horizontalPadding + paint.measureText(
            text,
            start,
            end
        ).roundToInt() + horizontalPadding
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }
}