package com.dicoding.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.activity.DetailUserActivity
import com.dicoding.githubuser.ui.activity.FavoriteActivity
import com.dicoding.githubuser.ui.activity.SettingActivity
import androidx.datastore.core.DataStore
import com.dicoding.githubuser.ui.adapter.UserAdapter
import com.dicoding.githubuser.ui.viewmodel.MainViewModel
import com.dicoding.githubuser.ui.viewmodel.SettingPreferencesViewModel
import com.dicoding.githubuser.ui.viewmodel.SettingViewModelFactory
import com.dicoding.githubuser.utils.SettingPreferences
import com.google.android.material.snackbar.Snackbar

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private val mainViewModel by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    checkTheme()
    super.onCreate(savedInstanceState)
    installSplashScreen()

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.github_users)

    mainViewModel.itemUser.observe(this) {
      setUserData(it)
    }

    setupRecycleView()

    mainViewModel.isLoading.observe(this) {
      showLoading(it)
    }

    showSnackBar()
  }

  private fun setupRecycleView() {
    binding.rvUsers.setHasFixedSize(true)
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
        mainViewModel.searchUser(query)
        searchView.clearFocus()
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle item selection
    return when (item.itemId) {
      R.id.btn_setting -> {
        val moveToSetting = Intent(this@MainActivity, SettingActivity::class.java)
        startActivity(moveToSetting)
        true
      }
      R.id.btn_favorite -> {
        val moveToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
        startActivity(moveToFavorite)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // add data from API to data class
  private fun setUserData(itemUser: List<ItemsItem>) {
    val listUser = ArrayList<ItemsItem>()
    for (user in itemUser) {
      val dataUser = ItemsItem(
        user.id,
        user.login,
        user.avatarUrl,
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

  private fun showSnackBar(){
    mainViewModel.snackBarText.observe(this) {
      it.getContentIfNotHandled()?.let { snackBarText ->
        Snackbar.make(
          findViewById(R.id.rv_users),
          snackBarText,
          Snackbar.LENGTH_SHORT
        ).show()
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.apply {
      if (isLoading) {
        progressBar.visibility = View.VISIBLE
        rvUsers.visibility = View.INVISIBLE
      } else {
        progressBar.visibility = View.GONE
        rvUsers.visibility = View.VISIBLE
      }
    }
  }

  private fun checkTheme() {
    val pref = SettingPreferences.getInstance(dataStore)
    val viewModel = ViewModelProvider(
      this,
      SettingViewModelFactory(pref)
    )[SettingPreferencesViewModel::class.java]

    // get last theme (dark mode/light mode)
    viewModel.getThemeSettings().observe(this) {
      if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
  }
}