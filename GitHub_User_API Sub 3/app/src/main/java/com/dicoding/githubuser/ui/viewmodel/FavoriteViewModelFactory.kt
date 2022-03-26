package com.dicoding.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteViewModelFactory private constructor(private val mApplication: Application) :
  ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
      return FavoriteViewModel(mApplication) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }

  companion object {
    @Volatile
    private var instance: FavoriteViewModelFactory? = null

    @JvmStatic
    fun getInstance(application: Application): FavoriteViewModelFactory =
      instance ?: synchronized(FavoriteViewModelFactory::class.java) {
        instance ?: FavoriteViewModelFactory(application)
      }

  }
}