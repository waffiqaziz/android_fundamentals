package com.dicoding.githubuser.views.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Event
import com.dicoding.githubuser.model.UserResponse
import com.dicoding.githubuser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
  private val _itemFollowers = MutableLiveData<List<UserResponse>>()
  val itemFollowers: LiveData<List<UserResponse>> = _itemFollowers

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _snackbarText = MutableLiveData<Event<String>>()
  val snackbarText: LiveData<Event<String>> = _snackbarText

  init{
    findFollowers("")
  }

  fun findFollowers(query: String) {
    if (query.isNotEmpty()) {
      _isLoading.value = true
      val client = ApiConfig.getApiService().getFollowers(query)
      client.enqueue(object : Callback<List<UserResponse>> {
        override fun onResponse(
          call: Call<List<UserResponse>>,
          response: Response<List<UserResponse>>
        ) {
          _isLoading.value = false
          if (response.isSuccessful) {
            _itemFollowers.value = response.body()
          } else {
            _snackbarText.value = Event(FAILED)
            Log.e(TAG, "onFailure: ${response.message()}")
          }
        }
        override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
          _isLoading.value = false
          _snackbarText.value = Event(FAILED)
          Log.e(TAG, "onFailure: ${t.message}")
        }
      })
    }
  }

  companion object {
    private const val TAG = "FollowersViewModel"
    private const val FAILED = "Connection Failed"
  }
}