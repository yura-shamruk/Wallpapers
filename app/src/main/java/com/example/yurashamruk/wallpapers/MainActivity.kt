package com.example.yurashamruk.wallpapers

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.yurashamruk.wallpapers.ui.view.WallpapersPreviewAnimated
import com.example.yurashamruk.wallpapers.util.RxUtil
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wallpapersPreview.trajectoryLineWidth = 4F.toPx()
        wallpapersPreview.setOnClickListener({ wallpapersPreview.startAnimation() })
    }



    override fun onPause() {
        super.onPause()
        wallpapersPreview.pauseAnimation()
    }

}
