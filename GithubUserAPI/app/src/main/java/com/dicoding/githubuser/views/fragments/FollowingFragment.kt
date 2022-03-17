package com.dicoding.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.githubuser.model.UserResponse
import com.dicoding.githubuser.views.activity.DetailUserActivity
import com.dicoding.githubuser.views.adapter.DetailUserAdapter
import com.dicoding.githubuser.views.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

  private var _binding: FragmentFollowingBinding? = null
  private val followingViewModel: FollowingViewModel by activityViewModels()

  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentFollowingBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val args = arguments
    val username = args?.getString(DetailUserActivity.EXTRA_USER).toString()

    followingViewModel.itemFollowing.observe(viewLifecycleOwner){
      setFollowing(it)
    }

    setupRecycleView()

    followingViewModel.isLoading.observe(viewLifecycleOwner){
      showLoading(it)
    }

    // find user following
    followingViewModel.findFollowing(username)
  }

  private fun setFollowing(itemUser: List<UserResponse>) {
    val listUser = ArrayList<UserResponse>()
    for (user in itemUser) {
      val dataUser = UserResponse(
        user.login,
        user.avatarUrl
      )
      listUser.add(dataUser)
    }

    val adapter =
      DetailUserAdapter(listUser)
    binding.rvUsers.adapter = adapter
  }

  private fun setupRecycleView() {
    val layoutManager = LinearLayoutManager(activity)
    binding.rvUsers.layoutManager = layoutManager
    val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
    binding.rvUsers.addItemDecoration(itemDecoration)
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}