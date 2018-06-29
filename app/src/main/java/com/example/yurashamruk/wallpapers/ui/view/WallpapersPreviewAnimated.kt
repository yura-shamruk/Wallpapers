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
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent


class WallpapersPreviewAnimated : View, MyGestureListener.GestureObserver {

    companion object {
        const val TAG = "WallpapersView"
        const val ANGLE_NAME = "angle"
        const val ANIMATION_TIME: Long = 9000
        const val WALLPAPER_WIDTH = 100
        const val WALLPAPER_HEIGHT = 166
    }

    private var myGestureDetector: GestureDetector? = null

    private val circlePaint: Paint? = Paint()

    private val trajectoryPaint: Paint? = Paint()

    var wallpapers: MutableList<WallpaperModel>? = ArrayList()

    var trajectoryLineWidth: Float = 0.0f
        set(value) {
            field = value
            initTrajectoryPaint()
            invalidate()
        }

    private var angle: Float = 180.0F
        set(value) {
            field = value
            invalidate()
        }

    private var startScrollAngle: Float = 0F

    private val trajectoryWidth = 100F.toPx()

    private val trajectoryHeight = 100F.toPx()

    private var objectAnimator: ObjectAnimator? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val viewCenterX = getViewCenterX()
        val viewCenterY = getViewCenterY()

        drawTrajectory(viewCenterX, viewCenterY, canvas)

        drawWallpapers(canvas)

        drawDegreesText(canvas)

    }


    override fun onScroll(startEvent: MotionEvent, currentEvent: MotionEvent) {
        stopAnimation()
        val startAngle: Number = Math.atan2((startEvent.y - getViewCenterY()).toDouble(), ((startEvent.x - getViewCenterX()).toDouble()))
        val startAngleDegrees = normalizeAngle(startAngle)
//        Log.i(TAG, "startAngleDegrees: $startAngleDegrees")

        val currentAngle: Number = Math.atan2((currentEvent.y - getViewCenterY()).toDouble(), ((currentEvent.x - getViewCenterX()).toDouble()))
        val currentAngleDegrees = normalizeAngle(currentAngle)
//        Log.i(TAG, "currentAngleDegrees: $currentAngleDegrees")

        val deltaAngleDegrees = startAngleDegrees - currentAngleDegrees

        Log.i(TAG, "deltaAngleDegrees: $deltaAngleDegrees")
        angle = (startScrollAngle + deltaAngleDegrees).toFloat()
        invalidate()
        startRotationAnimation(angle)
    }

    override fun onStartScroll() {
        Log.i(TAG, "startScrollAngle: $angle")
        startScrollAngle = angle
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

    private fun init() {
        circlePaint?.style = Paint.Style.FILL
        circlePaint?.isAntiAlias = true
        circlePaint?.color = Color.parseColor("#eeeeee")
        myGestureDetector = GestureDetector(context, MyGestureListener(this))
        initTrajectoryPaint()
    }


    private fun normalizeAngle(angle: Number): Double {
        var degrees = Math.toDegrees(angle as Double)
        Log.i("normalizeAngle", "degrees before: $degrees")
        if (degrees < 0) {
            degrees *= (-1)
        } else {
            degrees = 180 + 180 - degrees
        }
        if (degrees == 360.0) {
            degrees = 0.0
        }
        Log.i("normalizeAngle", "degrees after: $degrees")
        return degrees
    }

    private fun drawDegreesText(canvas: Canvas?) {
        val paint = Paint()
        paint.style = Paint.Style.FILL

        paint.color = Color.BLACK
        paint.textSize = 42f
        canvas?.drawText("" + angle, 50F, 50F, paint)
    }

    private fun startRotationAnimation(startAngle: Float) {
        objectAnimator?.removeAllListeners()
        objectAnimator?.end()
        objectAnimator?.cancel()
        val endAngle = startAngle + 360
        Log.i(TAG, "angle: $startAngle" + "endAngle: " + endAngle)
        objectAnimator = ObjectAnimator.ofFloat(this, ANGLE_NAME, startAngle, startAngle + 360).apply {
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (myGestureDetector != null && myGestureDetector!!.onTouchEvent(event)) {
            return true
        } else if (event.action == MotionEvent.ACTION_UP) {
            Log.v(TAG, "Up. angle: $angle")
        }

        return super.onTouchEvent(event)
    }

    private fun stopAnimation() {
        objectAnimator?.removeAllListeners()
        objectAnimator?.end()
        objectAnimator?.cancel()
    }


    private fun initTrajectoryPaint() {
        trajectoryPaint?.style = Paint.Style.STROKE
        trajectoryPaint?.strokeWidth = trajectoryLineWidth
        trajectoryPaint?.color = Color.parseColor("#cccccc")
    }


    private fun drawTrajectory(viewCenterX: Float, viewCenterY: Float, canvas: Canvas?) {
        val left = viewCenterX - trajectoryWidth
        val right = viewCenterX + trajectoryWidth
        val top = viewCenterY - trajectoryHeight
        val bottom = viewCenterY + trajectoryHeight
        canvas?.drawOval(left, top, right, bottom, trajectoryPaint)
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
        val circleCenterX = (trajectoryWidth * Math.cos(radiansAngle)).toFloat() + getViewCenterX()
        val circleCenterY = (trajectoryHeight * Math.sin(radiansAngle)).toFloat() + getViewCenterY()
        val imageView = wallpaper.imageView
        val drawable = imageView.drawable
        val left = circleCenterX - WALLPAPER_WIDTH.toPx() / 2
        val right = circleCenterX + WALLPAPER_WIDTH.toPx() / 2
        val top = circleCenterY - WALLPAPER_HEIGHT.toPx() / 2
        val bottom = circleCenterY + WALLPAPER_HEIGHT.toPx() / 2
        drawable.alpha = 240
        drawable.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        drawable.draw(canvas)
    }

    private fun updateStartAngle() {
        if (wallpapers == null) {
            return
        }
        val a = 360 / wallpapers!!.size
        wallpapers!!.forEachIndexed { index, wallpaperModel ->
            val angle = a * index
            wallpaperModel.startAngle = angle.toFloat()
        }
    }

}