package com.dicoding.githubuser.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.data.local.room.FavoriteDao
import com.dicoding.githubuser.data.local.room.FavoriteDatabase
import com.dicoding.githubuser.data.remote.response.UserDetailResponse
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import com.dicoding.githubuser.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
  private val _userDetail = MutableLiveData<UserDetailResponse>()
  val userDetail: LiveData<UserDetailResponse> = _userDetail

  private var favDao: FavoriteDao? = null
  private var favDb: FavoriteDatabase? = null

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

  init {
    favDb = FavoriteDatabase.getInstance(application)
    favDao = favDb?.favoriteDao()
  }

  // CHECK
  fun insertToFavorite(userId: Int, username: String, avatarURL: String){
    CoroutineScope(Dispatchers.IO).launch{
      val userIn = FavoriteEntity(
        userId, username, avatarURL
      )
      favDao?.insertFavorite(userIn)
    }
  }

  fun checkIsFavorited(id: Int) = favDao?.isFavorited(id)

  fun removeFromFavorite(id: Int){
    CoroutineScope(Dispatchers.IO).launch{
      favDao?.deleteUserFavorite(id)
    }
  }


  companion object {
    private const val TAG = "DetailViewModel"
    private const val FAILED = "Connection Failed"
  }
}