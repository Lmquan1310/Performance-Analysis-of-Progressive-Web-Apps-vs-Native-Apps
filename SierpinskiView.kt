package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class SierpinskiView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var maxDepth = 5
    private var drawingTime = 0L // Store the drawing time

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    // Add a callback for when drawing is complete
    var onDrawComplete: ((Long) -> Unit)? = null

    fun setDepth(depth: Int) {
        maxDepth = depth
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val startTime = System.nanoTime() // Start timing

        val width = width.toFloat()
        val height = height.toFloat()

        val p1 = Pair(width / 2, 0f)
        val p2 = Pair(0f, height)
        val p3 = Pair(width, height)

        drawSierpinski(canvas, p1, p2, p3, maxDepth)

        val endTime = System.nanoTime() // End timing
        drawingTime = (endTime - startTime) / 1_000_000 // Convert to milliseconds
        onDrawComplete?.invoke(drawingTime) // Notify about completion with time
    }

    private fun drawSierpinski(canvas: Canvas, p1: Pair<Float, Float>, p2: Pair<Float, Float>, p3: Pair<Float, Float>, depth: Int) {
        if (depth == 0) {
            val path = android.graphics.Path().apply {
                moveTo(p1.first, p1.second)
                lineTo(p2.first, p2.second)
                lineTo(p3.first, p3.second)
                close()
            }
            canvas.drawPath(path, paint)
            return
        }

        val mid1 = Pair((p1.first + p2.first) / 2, (p1.second + p2.second) / 2)
        val mid2 = Pair((p2.first + p3.first) / 2, (p2.second + p3.second) / 2)
        val mid3 = Pair((p3.first + p1.first) / 2, (p3.second + p1.second) / 2)

        drawSierpinski(canvas, p1, mid1, mid3, depth - 1)
        drawSierpinski(canvas, mid1, p2, mid2, depth - 1)
        drawSierpinski(canvas, mid3, mid2, p3, depth - 1)
    }
}
