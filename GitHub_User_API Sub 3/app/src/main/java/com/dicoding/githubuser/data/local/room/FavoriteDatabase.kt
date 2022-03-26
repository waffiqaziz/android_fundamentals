package com.dicoding.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubuser.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
  abstract fun favoriteDao(): FavoriteDao

  companion object {
    @Volatile
    private var instance: FavoriteDatabase? = null

    @JvmStatic
    fun getInstance(context: Context): FavoriteDatabase =
      instance ?: synchronized(this) {
        instance ?: Room.databaseBuilder(
          context.applicationContext,
          FavoriteDatabase::class.java,
          "Favorite.db"
        ).build()
      }
  }
}