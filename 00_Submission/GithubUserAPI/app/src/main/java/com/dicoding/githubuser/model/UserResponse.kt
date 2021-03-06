package com.dicoding.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String
)

