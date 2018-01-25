package com.phonepe.notely.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.phonepe.notely.R
import com.phonepe.notely.utils.start

/*
* Splash Activity
* */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()

        handler.postDelayed({
            start<HomeActivity>()
            finish()

        }, 1000)
    }
}
