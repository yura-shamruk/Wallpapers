package com.example.yurashamruk.wallpapers.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.yurashamruk.wallpapers.toPx

class WallpapersPreviewAnimated : View{

    companion object {
        const val ANGLE_NAME = "angle"
        const val ANIMATION_TIME:Long = 9000
    }

    private val circlePaint: Paint? = Paint()

    private val trajectoryPaint: Paint? = Paint()

    var wallpapers:List<WallpaperModel>? = ArrayList()

    private var circleCenterX : Float = 0F
    private var circleCenterY : Float = 0F
    private var angle : Float = 180.0F
        set(value) {
            field = value
            invalidate()
        }
    private val viewRadius = 25F.toPx()
    private val trajectoryRadius = 100F.toPx()

    private var objectAnimator: ObjectAnimator? = null

    var trajectoryLineWidth: Float = 0.0f
        set(value) {
            field = value
            initTrajectoryPaint()
            invalidate()
        }

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
        circlePaint?.color = Color.parseColor("#eeeeee")

        initTrajectoryPaint()
    }

    private fun initTrajectoryPaint() {
        trajectoryPaint?.style = Paint.Style.STROKE
        trajectoryPaint?.strokeWidth = trajectoryLineWidth
        trajectoryPaint?.color = Color.parseColor("#cccccc")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val viewCenterX =(this.measuredWidth / 2).toFloat()
        val viewCenterY = (this.measuredHeight / 2).toFloat()

        val radiansAngle = Math.toRadians(angle.toDouble())
        circleCenterX = (trajectoryRadius * Math.cos(radiansAngle)).toFloat() + viewCenterX
        circleCenterY = (trajectoryRadius * Math.sin(radiansAngle)).toFloat() + viewCenterY

        canvas?.drawCircle(viewCenterX, viewCenterY, trajectoryRadius, trajectoryPaint)

        drawWallpapers(canvas)
    }

    private fun drawWallpapers(canvas: Canvas?) {
        wallpapers?.forEach { it: WallpaperModel? -> drawWallpaper(canvas, it) }
    }

    private fun drawWallpaper(canvas: Canvas?, wallpaper: WallpaperModel?) {
        if(wallpaper == null){
            return
        }
        canvas?.drawCircle(circleCenterX, circleCenterY, wallpaper.radius, circlePaint)
    }



    fun startRotationAnimation() {
        objectAnimator = ObjectAnimator.ofFloat(this, ANGLE_NAME, -180f, 180f).apply {
            duration = ANIMATION_TIME
            interpolator = LinearInterpolator()
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }

    fun pauseAnimation(){
        objectAnimator?.pause()
    }

    fun resumeAnimation(){
        objectAnimator?.resume()
    }



}