package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Event
import com.dicoding.githubuser.service.repository.ApiConfig
import com.dicoding.githubuser.service.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
  private val _userDetail = MutableLiveData<UserDetailResponse>()
  val userDetail: LiveData<UserDetailResponse> = _userDetail

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _snackbarText = MutableLiveData<Event<String>>()
  val snackBarText: LiveData<Event<String>> = _snackbarText

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
            _snackbarText.value = Event(FAILED)
          }
        }

        override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
          _isLoading.value = false
          _snackbarText.value = Event(FAILED)
          Log.e(TAG, "onFailure: ${t.message}")
        }
      })
    }
  }

  companion object {
    private const val TAG = "DetailViewModel"
    private const val FAILED = "Connection Failed"
  }
}