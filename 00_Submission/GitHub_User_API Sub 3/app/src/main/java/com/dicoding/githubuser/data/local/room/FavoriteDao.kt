package com.dicoding.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM favorite ORDER BY login ASC")
  fun getFavorite(): LiveData<List<FavoriteEntity>>

  @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id)")
  suspend fun isFavorite(id: Int): Boolean

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

  @Delete
  suspend fun deleteUserFavorite(fav: FavoriteEntity)
}