package com.dicoding.githubuser.viewmodel

import androidx.lifecycle.*
import com.dicoding.githubuser.SettingPreferences
import kotlinx.coroutines.launch

class SettingPreferencesViewModel (private val pref: SettingPreferences) : ViewModel(){
  fun getThemeSettings(): LiveData<Boolean> {
    return pref.getThemeSetting().asLiveData()
  }

  fun saveThemeSetting(isDarkModeActive: Boolean) {
    viewModelScope.launch {
      pref.saveThemeSetting(isDarkModeActive)
    }
  }
}