package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Event
import com.dicoding.githubuser.service.repository.ApiConfig
import com.dicoding.githubuser.service.model.GitHubResponse
import com.dicoding.githubuser.service.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
  private val _itemUser = MutableLiveData<List<ItemsItem>>()
  val itemUser: LiveData<List<ItemsItem>> = _itemUser

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _snackBarText = MutableLiveData<Event<String>>()
  val snackBarText: LiveData<Event<String>> = _snackBarText

  init {
    searchUser("")
  }

  fun searchUser(query: String) {
    if (query.isNotEmpty()) {
      _isLoading.value = true
      val client = ApiConfig.getApiService().getSearchUser(query)
      client.enqueue(object : Callback<GitHubResponse> {
        override fun onResponse(
          call: Call<GitHubResponse>,
          response: Response<GitHubResponse>
        ) {
          _isLoading.value = false
          if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
              if (responseBody.totalCount == 0){
                _snackBarText.value = Event(NO_USER)
              }
              _itemUser.value = response.body()?.items
            }
          } else {
            Log.e(TAG, "onFailure: ${response.message()}")
            _snackBarText.value = Event(FAILED)
          }
        }

        override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
          _isLoading.value = false
          Log.e(TAG, "onFailure: ${t.message}")
          _snackBarText.value = Event(FAILED)
        }
      })
    }
  }

  companion object {
    private const val TAG = "MainViewModel"
    private const val FAILED = "Connection Failed"
    private const val NO_USER = "User Not Found"
  }
}