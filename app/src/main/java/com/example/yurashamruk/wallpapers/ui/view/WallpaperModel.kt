package com.example.yurashamruk.wallpapers.ui.view

import android.content.Context
import android.widget.ImageView
import com.example.yurashamruk.wallpapers.R
import com.example.yurashamruk.wallpapers.toPx

class WallpaperModel(context: Context) {
    var startAngle:Float? = 0.0f
    var radius:Float = 25f.toPx()
    var imageView:ImageView = ImageView(context)

    init {
        imageView.setImageResource(R.mipmap.ic_launcher)
    }

}