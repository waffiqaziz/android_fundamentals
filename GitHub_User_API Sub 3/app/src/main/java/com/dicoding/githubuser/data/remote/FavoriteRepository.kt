package com.dicoding.githubuser.data.remote

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.data.local.room.FavoriteDao
import com.dicoding.githubuser.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
  private val favDao: FavoriteDao
  private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

  init {
    val db = FavoriteDatabase.getInstance(application)
    favDao = db.favoriteDao()
  }

  fun getFavorite(): LiveData<List<FavoriteEntity>> = favDao.getFavorite()

  fun insert(fav: FavoriteEntity) {
    executorService.execute { favDao.insertFavorite(fav) }
  }

  fun delete(id: Int) {
    executorService.execute { favDao.deleteUserFavorite(id) }
  }
}