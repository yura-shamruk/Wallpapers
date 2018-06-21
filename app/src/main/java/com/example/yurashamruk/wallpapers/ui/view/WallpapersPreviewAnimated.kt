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
import android.widget.ImageView
import com.example.yurashamruk.wallpapers.R


class WallpapersPreviewAnimated : View {

    companion object {
        const val ANGLE_NAME = "angle"
        const val ANIMATION_TIME: Long = 9000
    }

    private val circlePaint: Paint? = Paint()

    private val trajectoryPaint: Paint? = Paint()

    var wallpapers: MutableList<WallpaperModel>? = ArrayList()

    private var angle: Float = 180.0F
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

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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
        val viewCenterX = getViewCenterX()
        val viewCenterY = getViewCenterY()


        canvas?.drawCircle(viewCenterX, viewCenterY, trajectoryRadius, trajectoryPaint)

        drawWallpapers(canvas)


    }


    private fun getViewCenterY(): Float {
        return (measuredHeight / 2).toFloat()
    }

    private fun getViewCenterX(): Float {
        return (this.measuredWidth / 2).toFloat()
    }

    private fun drawWallpapers(canvas: Canvas?) {
        wallpapers?.forEach { it: WallpaperModel? -> drawWallpaper(canvas, it) }
    }

    private fun drawWallpaper(canvas: Canvas?, wallpaper: WallpaperModel?) {
        val startAngle = wallpaper?.startAngle
        if (wallpaper == null || startAngle == null) {
            return
        }
        val radiansAngle = Math.toRadians(startAngle + angle.toDouble())
        val circleCenterX = (trajectoryRadius * Math.cos(radiansAngle)).toFloat() + getViewCenterX()
        val circleCenterY = (trajectoryRadius * Math.sin(radiansAngle)).toFloat() + getViewCenterY()
        val imageView = wallpaper.imageView
        val drawable = imageView.drawable
        val left = circleCenterX - drawable.intrinsicWidth / 2
        val top = circleCenterY - drawable.intrinsicHeight / 2
        val right = circleCenterX + drawable.intrinsicWidth / 2
        val bottom = circleCenterY + drawable.intrinsicHeight / 2
        drawable.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        drawable.draw(canvas)
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

    fun pauseAnimation() {
        objectAnimator?.pause()
    }

    fun resumeAnimation() {
        objectAnimator?.resume()
    }

    fun addWallpaper(wallpaperModel: WallpaperModel) {
        wallpapers?.add(wallpaperModel)
        updateStartAngle()
        invalidate()
    }

    private fun updateStartAngle() {

    }


}