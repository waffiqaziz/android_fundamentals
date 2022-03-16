package com.dicoding.githubuser.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.githubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

  private var _binding: FragmentFollowingBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
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
  }

  companion object {
    const val ARG_SECTION_NUMBER = "section_number"
  }
}