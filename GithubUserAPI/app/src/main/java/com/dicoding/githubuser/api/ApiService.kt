package com.dicoding.githubuser.api

import com.dicoding.githubuser.BuildConfig
import com.dicoding.githubuser.GitHubResponse
import com.dicoding.githubuser.UserDetailResponse
import com.dicoding.githubuser.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
  @GET("search/users")
  @Headers("Authorization: ${BuildConfig.API_KEY}")
  fun getSearchUser(
    @Query("q") search: String,
    @Query("per_page") perPage: Int = 100
  ): Call<GitHubResponse>

  @GET("users/{login}")
  @Headers("Authorization: ${BuildConfig.API_KEY}")
  fun getDetailUser(
    @Path("login") login: String
  ): Call<UserDetailResponse>

  @GET("users/{username}/followers")
  @Headers("Authorization: ${BuildConfig.API_KEY}")
  fun getFollowers(
    @Path("username") username: String
  ): Call<List<UserResponse>>

  @GET("users/{username}/following")
  @Headers("Authorization: ${BuildConfig.API_KEY}")
  fun getFollowing(
    @Path("username") username: String
  ): Call<List<UserResponse>>

}