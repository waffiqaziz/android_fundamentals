package com.dicoding.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.data.remote.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
  private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

  fun getFavorite(): LiveData<List<FavoriteEntity>> = favoriteRepository.getFavorite()
}