package com.dicoding.githubuser.views.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.GitHubResponse
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.UserDetailResponse
import com.dicoding.githubuser.UserResponse
import com.dicoding.githubuser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
  private val _itemFollowers = MutableLiveData<List<UserResponse>>()
  val itemFollowers: LiveData<List<UserResponse>> = _itemFollowers

  private val _itemFollowing = MutableLiveData<List<UserResponse>>()
  val itemFollowing: LiveData<List<UserResponse>> = _itemFollowers

  private val _userDetail = MutableLiveData<UserDetailResponse>()
  val userDetail: LiveData<UserDetailResponse> = _userDetail


  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  fun setUserDetail(query: String) {
    if (query.isNotEmpty()) {
      _isLoading.value = true
      val client = ApiConfig.getApiService().getDetailUser(query)
      client.enqueue(object : Callback<UserDetailResponse> {
        override fun onResponse(
          call: Call<UserDetailResponse>,
          response: Response<UserDetailResponse>
        ) {
          _isLoading.value = false
          if (response.isSuccessful) {
            _userDetail.value = response.body()
          } else {
            Log.e(TAG, "onFailure: ${response.message()}")
          }
        }
        override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
          _isLoading.value = false
          Log.e(TAG, "onFailure: ${t.message}")
        }
      })
    }
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
    private const val TAG = "DetailViewModel"
  }
}