package com.dicoding.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
class FavoriteEntity(
  @PrimaryKey
  @ColumnInfo(name = "id")
  val id: Int,

  @ColumnInfo(name = "login")
  val login: String,

  @ColumnInfo(name = "avatar_url")
  val avatar_url: String
) : Parcelable