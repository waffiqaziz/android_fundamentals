package com.dicoding.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM favorite ORDER BY login DESC")
  fun getFavorite(): LiveData<List<FavoriteEntity>>

  @Query("SELECT count(*) FROM favorite WHERE id = :id")
  fun isFavorited(id: Int): Int

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertFavorite(favoriteEntity: FavoriteEntity)

  @Query("DELETE FROM favorite WHERE id = :id")
  fun deleteUserFavorite(id: Int): Int
}