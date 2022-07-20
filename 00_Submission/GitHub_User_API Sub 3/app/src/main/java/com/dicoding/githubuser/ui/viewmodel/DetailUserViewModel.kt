package com.dicoding.githubuser.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.data.local.room.FavoriteRepository
import com.dicoding.githubuser.data.remote.response.UserDetailResponse
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import com.dicoding.githubuser.utils.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
  private val _userDetail = MutableLiveData<UserDetailResponse>()
  val userDetail: LiveData<UserDetailResponse> = _userDetail

  private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

  private val _snackbarText = MutableLiveData<Event<String>>()
  val snackBarText: LiveData<Event<String>> = _snackbarText

  fun setUserDetail(query: String) {
    if (query.isNotEmpty()) {
      val client = ApiConfig.getApiService().getDetailUser(query)
      client.enqueue(object : Callback<UserDetailResponse> {
        override fun onResponse(
          call: Call<UserDetailResponse>,
          response: Response<UserDetailResponse>
        ) {
          if (response.isSuccessful) {
            _userDetail.value = response.body()
          } else {
            Log.e(TAG, "onFailure: ${response.message()}")
            _snackbarText.value = Event(FAILED)
          }
        }

        override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
          _snackbarText.value = Event(FAILED)
          Log.e(TAG, "onFailure: ${t.message}")
        }
      })
    }
  }

  fun insertToFavorite(fav: FavoriteEntity) {
    viewModelScope.launch {
      favoriteRepository.insert(fav)
    }
  }

  suspend fun checkIsFavorite(id: Int) = favoriteRepository.isFavorite(id)

  fun removeFromFavorite(fav: FavoriteEntity) {
    viewModelScope.launch {
      favoriteRepository.delete(fav)
    }
  }

  companion object {
    private const val TAG = "DetailViewModel"
    private const val FAILED = "Connection Failed"
  }
}