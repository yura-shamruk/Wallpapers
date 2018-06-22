package com.example.yurashamruk.wallpapers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yurashamruk.wallpapers.ui.view.WallpaperModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillContent()

        wallpapersPreview.trajectoryLineWidth = 4F.toPx()
        wallpapersPreview.setOnClickListener {
            wallpapersPreview.startRotationAnimation()
        }
        wallpapersPreview.invalidate()
    }

    private fun fillContent() {
        wallpapersPreview.addWallpaper(WallpaperModel(this, R.drawable.wallpaper_1))
        wallpapersPreview.addWallpaper(WallpaperModel(this, R.drawable.wallpaper_2))
        wallpapersPreview.addWallpaper(WallpaperModel(this, R.drawable.wallpaper_3))
        wallpapersPreview.addWallpaper(WallpaperModel(this, R.drawable.wallpaper_3))
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
