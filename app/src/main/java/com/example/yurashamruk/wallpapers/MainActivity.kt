package com.example.yurashamruk.wallpapers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        wallpapersPreview.setOnClickListener({ startAnimation() })



    }

    private fun startAnimation() {
        disposable = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .compose(RxUtil.io())
                .subscribe({ wallpapersPreview.incrementAngle() }, { it.printStackTrace() })
    }

    private fun stopAnimation(){

    }
}
