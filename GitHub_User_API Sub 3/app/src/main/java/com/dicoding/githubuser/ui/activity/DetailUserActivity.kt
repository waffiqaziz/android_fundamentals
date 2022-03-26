package com.dicoding.githubuser.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.ui.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.ui.viewmodel.DetailUserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailUserBinding

  private val detailViewModel by viewModels<DetailUserViewModel>()

  // param helper
  private var isFavorited = false


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.detail_user)
    // showing back button in action bar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // get data user from MainActivity/FavoriteActivity
    lateinit var user: ItemsItem
    lateinit var userFav: FavoriteEntity
    lateinit var username: String
    lateinit var avatarURL: String
    val userId: Int

    if (intent.getParcelableExtra<ItemsItem>(EXTRA_USER) != null) {
      user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem
      username = user.login
      avatarURL = user.avatarUrl
      userId = user.id
    } else {
      userFav = intent.getParcelableExtra(EXTRA_FAVORITE)!!
      avatarURL = userFav.avatar_url
      username = userFav.login
      userId = userFav.id
    }

    detailViewModel.isLoading.observe(this) {
      showLoading(it)
    }

    // set user detail
    setUserDetail(username)


    // check is user has been favorited/no
    isFavorited = false
    checkIsFavorited(userId)

    detailViewModel.snackBarText.observe(this) {
      it.getContentIfNotHandled()?.let { snackBarText: String ->
        val snackBar =
          Snackbar.make(findViewById(R.id.view_pager), snackBarText, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Back") {
          finish()
        }
        snackBar.show()
      }
    }

    // put username to bundle
    val bundle = Bundle()
    bundle.putString(EXTRA_USER, username)

    setAdapter(bundle)

    btnFavoriteAction(userId, username, avatarURL)
  }

  private fun setAdapter(bundle: Bundle) {
    val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
    binding.viewPager.adapter = sectionsPagerAdapter
    TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
      tab.text = resources.getString(TAB_TITLES[position])
    }.attach()
  }

  private fun setUserDetail(username: String) {
    detailViewModel.setUserDetail(username)
    detailViewModel.userDetail.observe(this) {
      if (it != null) {
        val text = "@${it.login}"
        with(binding) {
          tvFollowers.text = it.followers.toString()
          tvFollowing.text = it.following.toString()
          tvName.text = it.name
          tvUsername.text = text
          tvCompany.text = it.company ?: "-"
          tvRepository.text = it.publicRepos.toString()
          tvLocation.text = it.location ?: "-"

          Glide.with(ivAvatar)
            .load(it.avatarUrl) // URL Avatar
            .circleCrop() // change avatar to circle
            .into(ivAvatar)
        }
      }
    }
  }

  // implement back button
  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  private fun showLoading(isLoading: Boolean) {
    binding.apply {
      if (isLoading) {
        progressBar.visibility = View.VISIBLE
        identity.visibility = View.INVISIBLE
        popView.visibility = View.INVISIBLE
        tabLayoutViewPager.visibility = View.INVISIBLE
      } else {
        progressBar.visibility = View.INVISIBLE
        identity.visibility = View.VISIBLE
        popView.visibility = View.VISIBLE
        tabLayoutViewPager.visibility = View.VISIBLE
      }
    }
  }

  private fun checkIsFavorited(id: Int) {
    CoroutineScope(Dispatchers.IO).launch {
      val result = detailViewModel.checkIsFavorited(id)
      withContext(Dispatchers.Main) {
        if (result != null) {
          if (result > 0) {
            isFavorited = true
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_fill)
          } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_no_fill)
            isFavorited = false
          }
        }
      }
    }
  }

  private fun btnFavoriteAction(userId: Int, username: String, avatarURL: String) {
    binding.btnFavorite.setOnClickListener {
      if (!isFavorited) {
        isFavorited = true
        detailViewModel.insertToFavorite(userId, username, avatarURL)
        binding.btnFavorite.setImageResource(R.drawable.ic_favorite_fill)
      } else {
        showAlertDialog(userId)
      }
    }
  }

  private fun showAlertDialog(id: Int) {
    val alertDialogBuilder = AlertDialog.Builder(this)
    with(alertDialogBuilder) {
      setTitle(R.string.delete)
      setMessage(R.string.message_delete)
      setCancelable(false)
      setPositiveButton(getString(R.string.yes)) { _, _ ->
        isFavorited = false
        binding.btnFavorite.setImageResource(R.drawable.ic_favorite_no_fill)
        detailViewModel.removeFromFavorite(id) // delete user from favorite
        showToast(getString(R.string.deleted))
        finish()
      }
      setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
    }
    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
  }

  private fun showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  companion object {
    const val EXTRA_USER = "user"
    const val EXTRA_FAVORITE = "favorite"

    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.following,
      R.string.followers
    )
  }

}