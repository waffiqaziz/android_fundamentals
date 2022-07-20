package com.dicoding.githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailUserBinding

  @SuppressLint("StringFormatMatches")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail_user)

    // change tittle action bar
    supportActionBar?.title = getString(R.string.detail_user)
    // showing the back button in action bar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // view binding
    binding = ActivityDetailUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
    val text = "@" + user.username
    binding.apply {
      tvFollowers.text = user.followers
      tvFollowing.text = user.following
      tvName.text = user.name
      tvUsername.text = text
      tvCompany.text = user.company
      tvRepository.text = user.repository
      tvLocation.text = user.location
      Glide.with(ivAvatar)
        .load(user.avatar) // URL Avatar
        .circleCrop() // change avatar to circle
        .into(ivAvatar)
    }
  }

  // implement back button
  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  companion object {
    const val EXTRA_USER = "user"
  }
}