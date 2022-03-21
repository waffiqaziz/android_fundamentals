package com.dicoding.githubuser.views.activity

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.R
import com.dicoding.githubuser.SettingPreferences
import com.dicoding.githubuser.databinding.ActivitySettingBinding
import com.dicoding.githubuser.viewmodel.SettingPreferencesViewModel
import com.dicoding.githubuser.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySettingBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySettingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.setting)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val pref = SettingPreferences.getInstance(dataStore)
    val viewModel = ViewModelProvider(this,
      ViewModelFactory(pref)
    )[SettingPreferencesViewModel::class.java]

    viewModel.getThemeSettings().observe(
      this
    ) { isDarkModeActive: Boolean ->
      if (isDarkModeActive) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding.switchTheme.isChecked = true
      } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.switchTheme.isChecked = false
      }
    }

    binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
      viewModel.saveThemeSetting(isChecked)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }
}