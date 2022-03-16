package com.dicoding.githubuser.views.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.views.fragments.FollowersFragment
import com.dicoding.githubuser.views.fragments.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

  override fun createFragment(position: Int): Fragment {
    var fragment: Fragment? = null
    when (position) {
      0 -> fragment = FollowersFragment()
      1 -> fragment = FollowingFragment()
    }
    return fragment as Fragment
  }

  override fun getItemCount() = 2
}