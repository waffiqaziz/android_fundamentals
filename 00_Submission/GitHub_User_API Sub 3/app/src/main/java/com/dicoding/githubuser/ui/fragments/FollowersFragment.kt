package com.dicoding.githubuser.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.githubuser.data.remote.response.UserResponse
import com.dicoding.githubuser.ui.activity.DetailUserActivity.Companion.EXTRA_USER
import com.dicoding.githubuser.ui.adapter.DetailUserAdapter
import com.dicoding.githubuser.ui.viewmodel.FollowersViewModel
import com.google.android.material.snackbar.Snackbar


class FollowersFragment : Fragment() {

  private var _binding: FragmentFollowersBinding? = null
  private val followersViewModel: FollowersViewModel by activityViewModels()

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

    val args = arguments
    val username = args?.getString(EXTRA_USER).toString()

    followersViewModel.itemFollowers.observe(viewLifecycleOwner) {
      setFollowers(it)
    }

    setupRecycleView()

    followersViewModel.isLoading.observe(viewLifecycleOwner) {
      showLoading(it)
    }

    // find user followers
    followersViewModel.findFollowers(username)

    showSnackBar()
  }

  private fun showSnackBar(){
    followersViewModel.snackBarText.observe(viewLifecycleOwner) {
      it.getContentIfNotHandled()?.let { snackBarText ->
        Snackbar.make(
          requireActivity().findViewById(R.id.followers),
          snackBarText,
          Snackbar.LENGTH_LONG
        ).show()
      }
    }
  }

  private fun setupRecycleView() {
    val layoutManager = LinearLayoutManager(activity)
    binding.rvUsers.layoutManager = layoutManager
    val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
    binding.rvUsers.addItemDecoration(itemDecoration)
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

    val adapter = DetailUserAdapter(listUser)
    binding.rvUsers.adapter = adapter
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
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