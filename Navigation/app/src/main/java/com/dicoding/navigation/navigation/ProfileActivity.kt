package com.dicoding.navigation.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.navigation.R

class ProfileActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)
    supportActionBar?.title = getString(R.string.profile_activity)
  }
}