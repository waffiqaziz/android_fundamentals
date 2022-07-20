package com.dicoding.githubuser.ui.activity

import android.os.Bundle
import android.view.View
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
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    getAllFavorite()

    adapter = FavoriteAdapter()

    binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
    binding?.rvUsers?.setHasFixedSize(true)
    binding?.rvUsers?.adapter = adapter
  }

  private fun getAllFavorite() {
    val viewModel = obtainViewModel(this@FavoriteActivity)
    viewModel.getFavorite().observe(this) {
      if (it != null) {
        adapter.setListFav(it)
      }

      // show information(has favorite data/not)
      if (adapter.itemCount == 0) {
        binding?.rvUsers?.visibility = View.GONE
        binding?.tvNoData?.visibility = View.VISIBLE
      } else {
        binding?.tvNoData?.visibility = View.GONE
      }
    }
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

  // auto update data
  override fun onResume() {
    super.onResume()
    getAllFavorite() // get the latest data from favorite database
  }
}
