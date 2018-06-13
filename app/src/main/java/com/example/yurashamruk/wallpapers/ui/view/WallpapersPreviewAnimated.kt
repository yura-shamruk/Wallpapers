package com.example.yurashamruk.wallpapers.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.yurashamruk.wallpapers.toPx

class WallpapersPreviewAnimated : View{

    private val circlePaint: Paint? = Paint()

    private var circleCenterX : Float = 0F
    private var circleCenterY : Float = 0F
    private var angle : Double = 180.0
    private val viewRadius = 25F.toPx()
    private val trajectoryRadius = 100F.toPx()

    constructor(context: Context?) : super(context){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init() {
        circlePaint?.style = Paint.Style.FILL
        circlePaint?.isAntiAlias = true
        circlePaint?.color = Color.parseColor("#cccccc")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val viewCenterX =(this.measuredWidth / 2).toFloat()
        val viewCenterY = (this.measuredHeight / 2).toFloat()

        val radiansAngle = Math.toRadians(angle)
        circleCenterX = (trajectoryRadius * Math.cos(radiansAngle)).toFloat() + viewCenterX
        circleCenterY = (trajectoryRadius * Math.sin(radiansAngle)).toFloat() + viewCenterY

        canvas?.drawCircle(circleCenterX, circleCenterY, viewRadius, circlePaint)
    }

    fun incrementAngle(){
        angle = angle.plus(15)
        invalidate()
    }
}