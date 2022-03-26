package com.dicoding.githubuser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.adapter.FavoriteAdapter
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModel
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModelFactory


class FavoriteActivity : AppCompatActivity() {

  private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
  private val binding get() = _activityFavoriteBinding
  private lateinit var adapter: FavoriteAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
    setContentView(binding?.root)

    supportActionBar?.title = getString(R.string.favorite_user)

    // showing back button in action bar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    setupRecycleView()
  }

  private fun setupRecycleView() {
    val viewModel = obtainViewModel(this@FavoriteActivity)
    viewModel.getFavorite().observe(this) {
      if (it != null) {
        adapter.setListFav(it)
      }
    }

    adapter = FavoriteAdapter()
    binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
    binding?.rvUsers?.setHasFixedSize(true)
    binding?.rvUsers?.adapter = adapter
  }

  private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
    val factory = FavoriteViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
  }

  // implement back button
  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    _activityFavoriteBinding = null
  }
}
