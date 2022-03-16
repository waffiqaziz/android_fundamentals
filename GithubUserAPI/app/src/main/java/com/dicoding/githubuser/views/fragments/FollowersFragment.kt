package com.dicoding.githubuser.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.R
import com.dicoding.githubuser.UserResponse
import com.dicoding.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.githubuser.views.activity.DetailUserActivity
import com.dicoding.githubuser.views.adapter.DetailUserAdapter


class FollowersFragment : Fragment() {

  private var _binding: FragmentFollowersBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentFollowersBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  private fun setFollowers(itemUser: List<UserResponse>) {
    val listUser = ArrayList<UserResponse>()
    for (user in itemUser) {
      val dataUser = UserResponse(
        user.login,
        user.avatarUrl
      )
      listUser.add(dataUser)
    }

    val adapter =
      DetailUserAdapter(listUser ,object : DetailUserAdapter.OnItemClickCallback{
        override fun onItemClicked(data: ItemsItem) {
          val moveUserDetail = Intent(activity, DetailUserActivity::class.java)
          moveUserDetail.putExtra(DetailUserActivity.EXTRA_USER, data)
          startActivity(moveUserDetail)
        }
      })
    binding.rvUsers.adapter = adapter
  }

  companion object {
    const val ARG_SECTION_NUMBER = "section_number"
  }
}