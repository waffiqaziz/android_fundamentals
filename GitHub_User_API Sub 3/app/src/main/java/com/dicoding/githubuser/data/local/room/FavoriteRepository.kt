package com.dicoding.githubuser.data.local.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.entity.FavoriteEntity

class FavoriteRepository(application: Application) {
  private val favDao: FavoriteDao

  init {
    val db = FavoriteDatabase.getInstance(application)
    favDao = db.favoriteDao()
  }

  fun getFavorite(): LiveData<List<FavoriteEntity>> = favDao.getFavorite()

  suspend fun insert(fav: FavoriteEntity) {
    favDao.insertFavorite(fav)
  }

  suspend fun delete(fav: FavoriteEntity) {
    favDao.deleteUserFavorite(fav)
  }

  suspend fun isFavorite(id: Int) = favDao.isFavorite(id)
}