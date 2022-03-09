package com.dicoding.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
  var username: String,
  var name: String,
  var avatar: Int,
  var followers: String,
  var following: String,
  var company: String,
  var location: String,
  var repository: String,
) : Parcelable