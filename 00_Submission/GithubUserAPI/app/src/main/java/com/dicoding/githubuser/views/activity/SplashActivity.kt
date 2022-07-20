package com.dicoding.githubuser.views.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubuser.MainActivity
import com.dicoding.githubuser.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val timeToWait = 2000L // 2 second

    requestWindowFeature(Window.FEATURE_NO_TITLE)// hide title
    supportActionBar?.hide() //hide title bar

    @Suppress("DEPRECATION")
    this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen

    setContentView(R.layout.activity_splash)

    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this@SplashActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }, timeToWait)
  }
}