package com.example.photo.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


class CustomButtomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attributeSet, defStyleAttrs) {

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL

        val radius = min(width, height) / 2 - 10
        canvas.drawCircle(width / 2f, height / 2f, radius.toFloat(), paint)
        canvas.drawLine(width / 2f - 92f, height / 2f, width / 2 + 92f,height / 2f,paint)
        canvas.drawLine(width / 2f, height / 2f - 92f, width / 2f,height / 2f + 92f,paint)

        paint.color = Color.WHITE
        canvas.drawLine(width / 2f, height / 2f, width / 2 + 44f,height / 2f,paint)
        canvas.drawLine(width / 2f, height / 2f - 44f, width / 2f,height / 2f + 5f,paint)
    }
}
