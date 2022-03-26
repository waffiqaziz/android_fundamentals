package com.dicoding.githubuser.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.di.Injection
import com.dicoding.githubuser.utils.SettingPreferences

class SettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SettingPreferencesViewModel::class.java)) {
      return SettingPreferencesViewModel(pref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }

}