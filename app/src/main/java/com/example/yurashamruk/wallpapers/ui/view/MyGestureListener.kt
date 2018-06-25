package com.example.yurashamruk.wallpapers.ui.view

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MyGestureListener(private val gestureObserver: GestureObserver) : GestureDetector.SimpleOnGestureListener() {

    override fun onDown(event: MotionEvent): Boolean {
        Log.i("TAG", "onDown: ")

        // don't return false here or else none of the other
        // gestures will work

        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.i("TAG", "onSingleTapConfirmed: ")
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.i("TAG", "onLongPress: ")
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.i("TAG", "onDoubleTap: ")
        return true
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                          distanceX: Float, distanceY: Float): Boolean {
        gestureObserver.onScroll(e1, e2)
        return true
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent,
                         velocityX: Float, velocityY: Float): Boolean {
        Log.i("TAG", "onFling: ")
        return true
    }



    interface GestureObserver{
        fun onScroll(startEvent: MotionEvent, currentEvent: MotionEvent)
        fun onStartScroll()
    }
}