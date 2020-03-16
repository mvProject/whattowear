package com.kinectpro.whattowear.helpers

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import kotlin.math.roundToInt

class RoundedBackgroundSpan(
    backgroundColor: Int,
    textColor: Int,
    cornerRadius: Int,
    horizontalPadding: Int,
    verticalPadding: Int
) : ReplacementSpan() {
    private var cornerRadius = 8
    private var backgroundColor = 0
    private var textColor = 0
    private var horizontalPadding = 0
    private var verticalPadding = 0

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
            x + measureText(paint, text, start, end) + 2 * horizontalPadding,
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
        fm: Paint.FontMetricsInt?
    ): Int {
        return 2 * horizontalPadding + paint.measureText(text, start, end).roundToInt()
    }


    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }


    init {
        this.backgroundColor = backgroundColor
        this.cornerRadius = cornerRadius
        this.textColor = textColor
        this.verticalPadding = verticalPadding
        this.horizontalPadding = horizontalPadding
    }
}