package com.example.yurashamruk.wallpapers

import android.content.res.Resources

fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)

fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()