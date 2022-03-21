package com.dicoding.githubuser.views.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.service.model.ItemsItem
import com.dicoding.githubuser.views.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.viewmodel.DetailUserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailUserBinding

  private val detailViewModel by viewModels<DetailUserViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.detail_user)

    // showing the back button in action bar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // get data user clicked from MainActivity
    val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

    // put username to bundle
    val bundle = Bundle()
    bundle.putString(EXTRA_USER, user.login)

    detailViewModel.isLoading.observe(this) {
      showLoading(it)
    }

    // set user detail
    detailViewModel.setUserDetail(user.login)
    detailViewModel.userDetail.observe(this) {
      if (it != null) {
        val text = "@${it.login}"
        binding.apply {
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

    val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
    binding.viewPager.adapter = sectionsPagerAdapter
    TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
      tab.text = resources.getString(TAB_TITLES[position])
    }.attach()
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

  companion object {
    const val EXTRA_USER = "user"

    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.following,
      R.string.followers
    )
  }

}