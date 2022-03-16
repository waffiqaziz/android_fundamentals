package com.dicoding.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.views.activity.DetailUserActivity
import com.dicoding.githubuser.views.adapter.UserAdapter
import com.dicoding.githubuser.views.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  private val mainViewModel by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.github_users)

    binding.rvUsers.setHasFixedSize(true)

    mainViewModel.itemUser.observe(this) {
      setUserData(it)
    }

    setupRecycleView()

    mainViewModel.isLoading.observe(this) {
      showLoading(it)
    }

    mainViewModel.snackbarText.observe(this) {
      it.getContentIfNotHandled()?.let { snackBarText ->
        Snackbar.make(
          window.decorView.rootView,
          snackBarText,
          Snackbar.LENGTH_SHORT
        ).show()
      }
    }
  }

  private fun setupRecycleView() {
    val layoutManager = LinearLayoutManager(this)
    binding.rvUsers.layoutManager = layoutManager
    val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
    binding.rvUsers.addItemDecoration(itemDecoration)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.option_menu, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    val searchView = menu.findItem(R.id.search).actionView as SearchView

    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    searchView.queryHint = resources.getString(R.string.search_hint)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        mainViewModel.findUser(query)
        searchView.clearFocus()
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
    return true
  }

  // add data from API to data class
  private fun setUserData(itemUser: List<ItemsItem>) {
    val listUser = ArrayList<ItemsItem>()
    for (user in itemUser) {
      val dataUser = ItemsItem(
        user.id,
        user.login,
        user.avatarUrl,
        user.url,
        user.followingUrl,
        user.followersUrl
      )
      listUser.add(dataUser)
    }

    val adapter =
      UserAdapter(listUser, object : UserAdapter.OnItemClickCallback {
        override fun onItemClicked(data: ItemsItem) {
          val moveUserDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
          moveUserDetail.putExtra(DetailUserActivity.EXTRA_USER, data)
          startActivity(moveUserDetail)
        }
      })
    binding.rvUsers.adapter = adapter
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
      binding.progressBar.visibility = View.VISIBLE
      binding.rvUsers.visibility = View.INVISIBLE
    } else {
      binding.progressBar.visibility = View.GONE
      binding.rvUsers.visibility = View.VISIBLE
    }
  }
}