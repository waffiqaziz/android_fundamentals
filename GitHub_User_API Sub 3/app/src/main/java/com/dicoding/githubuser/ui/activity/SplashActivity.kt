package com.dicoding.githubuser.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.MainActivity
import com.dicoding.githubuser.R
import com.dicoding.githubuser.utils.SettingPreferences
import com.dicoding.githubuser.ui.viewmodel.SettingViewModelFactory
import com.dicoding.githubuser.ui.viewmodel.SettingPreferencesViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


    checkTheme()

    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(Intent(this@SplashActivity, MainActivity::class.java))
      finish()
    }, timeToWait)

    requestWindowFeature(Window.FEATURE_NO_TITLE)// hide title
    supportActionBar?.hide() //hide title bar

    @Suppress("DEPRECATION")
    this.window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )

    setContentView(R.layout.activity_splash)
  }

  private fun checkTheme() {
    val pref = SettingPreferences.getInstance(dataStore)
    val viewModel = ViewModelProvider(
      this,
      SettingViewModelFactory(pref)
    )[SettingPreferencesViewModel::class.java]

    // get last theme (dark mode/light mode)
    viewModel.getThemeSettings().observe(this) {
      if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
  }

  companion object{
    const val timeToWait = 2000L // 2 second
  }
}