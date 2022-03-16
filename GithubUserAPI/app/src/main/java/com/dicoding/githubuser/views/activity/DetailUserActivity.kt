package com.dicoding.githubuser.views.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.views.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.views.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailUserBinding

  private val detailViewModel by viewModels<DetailUserViewModel>()

  @SuppressLint("StringFormatMatches")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail_user)

    val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

    // change tittle action bar
    supportActionBar?.title = getString(R.string.detail_user)
    // showing the back button in action bar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // view binding
    binding = ActivityDetailUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    detailViewModel.setUserDetail(user.login)
    detailViewModel.userDetail.observe(this){
      if(it != null){
        val text = "@" + it.login
        binding.apply {
          tvFollowers.text = it.followers.toString()
          tvFollowing.text = it.following.toString()
          tvName.text = it.name
          tvUsername.text = text
          tvCompany.text = it.company?: "-"
          tvRepository.text = it.publicRepos.toString()
          tvLocation.text = it.location?: "-"
          Glide.with(ivAvatar)
            .load(it.avatarUrl) // URL Avatar
            .circleCrop() // change avatar to circle
            .into(ivAvatar)
        }
      }
    }

    val sectionsPagerAdapter = SectionsPagerAdapter(this)
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
    if (isLoading) binding.progressBar.visibility = View.VISIBLE
    else binding.progressBar.visibility = View.GONE
  }

  companion object {
    const val EXTRA_USER = "user"
    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.followers,
      R.string.following
    )
  }
}