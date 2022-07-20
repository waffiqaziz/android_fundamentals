package com.dicoding.githubuser.api

import com.dicoding.githubuser.BuildConfig
import com.dicoding.githubuser.model.GitHubResponse
import com.dicoding.githubuser.model.UserDetailResponse
import com.dicoding.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
  @GET("search/users")
  @Headers("Authorization: token ${BuildConfig.API_KEY}")
  fun getSearchUser(
    @Query("q") search: String,
    @Query("per_page") perPage: Int = 100
  ): Call<GitHubResponse>

  @GET("users/{login}")
  @Headers("Authorization: token ${BuildConfig.API_KEY}")
  fun getDetailUser(
    @Path("login") login: String
  ): Call<UserDetailResponse>

  @GET("users/{username}/followers")
  @Headers("Authorization: token ${BuildConfig.API_KEY}")
  fun getFollowers(
    @Path("username") username: String,
    @Query("per_page") perPage: Int = 100
  ): Call<List<UserResponse>>

  @GET("users/{username}/following")
  @Headers("Authorization: token ${BuildConfig.API_KEY}")
  fun getFollowing(
    @Path("username") username: String,
    @Query("per_page") perPage: Int = 100
  ): Call<List<UserResponse>>

}