package com.example.yurashamruk.wallpapers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yurashamruk.wallpapers.ui.view.WallpaperModel
import com.example.yurashamruk.wallpapers.ui.view.WallpapersPreviewAnimated
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var list = ArrayList<WallpaperModel>()
        list.add(WallpaperModel())
        list.add(WallpaperModel())
//        wallpapersPreview.wallpapers =
        wallpapersPreview.trajectoryLineWidth = 4F.toPx()
        wallpapersPreview.setOnClickListener({
            wallpapersPreview.startRotationAnimation()
        })
    }

    override fun onResume() {
        super.onResume()
        wallpapersPreview.resumeAnimation()
    }

    override fun onPause() {
        super.onPause()
        wallpapersPreview.pauseAnimation()
    }

}
