package com.dicoding.githubuser.views.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Event
import com.dicoding.githubuser.api.ApiConfig
import com.dicoding.githubuser.model.GitHubResponse
import com.dicoding.githubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
  private val _itemUser = MutableLiveData<List<ItemsItem>>()
  val itemUser: LiveData<List<ItemsItem>> = _itemUser

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _snackbarText = MutableLiveData<Event<String>>()
  val snackbarText: LiveData<Event<String>> = _snackbarText

  init {
    findUser("")
  }

  fun findUser(query: String) {
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
                _snackbarText.value = Event(NO_USER)
              }
              _itemUser.value = response.body()?.items
            }
          } else {
            Log.e(TAG, "onFailure: ${response.message()}")
            _snackbarText.value = Event(FAILED)
          }
        }

        override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
          _isLoading.value = false
          Log.e(TAG, "onFailure: ${t.message}")
          _snackbarText.value = Event(FAILED)
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