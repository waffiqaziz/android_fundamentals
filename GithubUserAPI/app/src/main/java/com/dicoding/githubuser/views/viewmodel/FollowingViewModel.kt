package com.dicoding.githubuser.views.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.api.ApiConfig
import com.dicoding.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

  private val _itemFollowing = MutableLiveData<List<UserResponse>>()
  val itemFollowing: LiveData<List<UserResponse>> = _itemFollowing

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  init{
    findFollowing("")
  }

  fun findFollowing(query: String) {
    if (query.isNotEmpty()) {
      _isLoading.value = true
      val client = ApiConfig.getApiService().getFollowing(query)
      client.enqueue(object : Callback<List<UserResponse>> {
        override fun onResponse(
          call: Call<List<UserResponse>>,
          response: Response<List<UserResponse>>
        ) {
          _isLoading.value = false
          if (response.isSuccessful) {
            _itemFollowing.value = response.body()
          } else {
            Log.e(TAG, "onFailure: ${response.message()}")
          }
        }
        override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
          _isLoading.value = false
          Log.e(TAG, "onFailure: ${t.message}")
        }
      })
    }
  }

  companion object {
    private const val TAG = "FollowingViewModel"
  }
}