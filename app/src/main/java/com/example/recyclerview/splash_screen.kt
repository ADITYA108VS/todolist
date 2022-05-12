package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*
import kotlin.concurrent.schedule

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val img = findViewById<ImageView>(R.id.img)
        img.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                androidx.constraintlayout.widget.R.anim.abc_slide_in_top
            )
        )
        img.animate().apply {
            rotationY(360f)
            duration = 1500
        }.withEndAction {
           img.animate().apply {
               rotationX(360f)
               duration=1500
           }
       }.start()
        Timer().schedule(3500){
          l()
        }

    }
    private fun l()
    {
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}