package com.example.photo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GestureDetectorCompat
import kotlin.math.min


class CustomButtomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attributeSet, defStyleAttrs) {

    val star =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_add_24)!!.toBitmap()
    var imageCoords: Pair<Float, Float>? = null
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    val recognizer = GestureDetectorCompat(context, object :
        GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            imageCoords = e!!.x to e.y
            invalidate()
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            imageCoords = e2!!.x to e2.y
            invalidate()
            return true
        }
    })

    init {
        setOnTouchListener { view, motionEvent ->

            recognizer.onTouchEvent(motionEvent)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL

        val radius = min(width, height) / 2 - 10
        canvas.drawCircle(width / 2f, height / 2f, radius.toFloat(), paint)
        canvas.drawLine(640f, 80f, 440f,80f,paint)
        canvas.drawLine(540f, -10f, 540f,180f,paint)

        paint.color = Color.WHITE
        canvas.drawLine(590f, 80f, 540f,80f,paint)
        canvas.drawLine(540f, 30f, 540f,85f,paint)
    }
}
