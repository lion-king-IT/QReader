package com.reo.running.qreader

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : AppCompatActivity() {
    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        animation(qr_text)
        loadingDelay()
    }

    fun loadingDelay() {
        handler.postDelayed({
            animation2(qr_text)
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        },6000)
    }

    private fun animation(qr_text:TextView) {
        val objectAnimator = ObjectAnimator.ofFloat(qr_text,"translationX",1280f)
        objectAnimator.duration = 4000
        objectAnimator.start()
    }

    private fun animation2(qr_text: TextView) {
        val objectAnimation2 = ObjectAnimator.ofFloat(qr_text,"alpha",1f,0f)
        objectAnimation2.duration = 2000
        objectAnimation2.repeatCount = 1
        objectAnimation2.start()
    }
}