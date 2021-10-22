package com.example.anvisdigitalassignment.ui.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.anvisdigitalassignment.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private  lateinit var binding: ActivitySplashBinding

    var delay : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgDurar

        //set full Screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        , WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Initialize animation
        val objectAnimator:ObjectAnimator= ObjectAnimator.ofPropertyValuesHolder(
            binding.imgDurar,
            PropertyValuesHolder.ofFloat("scaleX",1.5f),
            PropertyValuesHolder.ofFloat("scaleY",1.5f),
        ) as ObjectAnimator

        //set Duration
        objectAnimator.setDuration(500)

        //setRepeat Count
        objectAnimator.repeatCount = ValueAnimator.INFINITE

        //setRepeat Modez
        objectAnimator.repeatMode = ValueAnimator.REVERSE

        //start Animation
        objectAnimator.start()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },delay)
    }
}