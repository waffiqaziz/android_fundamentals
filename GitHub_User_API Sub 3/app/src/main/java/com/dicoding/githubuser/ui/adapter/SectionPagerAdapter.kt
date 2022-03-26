package com.dicoding.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.ui.fragments.FollowersFragment
import com.dicoding.githubuser.ui.fragments.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle) :
  FragmentStateAdapter(activity) {

  private var fragmentBundle: Bundle = data

  override fun createFragment(position: Int): Fragment {
    var fragment: Fragment? = null
    when (position) {
      0 -> fragment = FollowingFragment()
      1 -> fragment = FollowersFragment()
    }
    fragment?.arguments = this.fragmentBundle
    return fragment as Fragment
  }

  override fun getItemCount() = 2
}