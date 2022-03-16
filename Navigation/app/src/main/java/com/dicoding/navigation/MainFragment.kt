package com.dicoding.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.dicoding.navigation.actionbar.ActionBarMainActivity
import com.dicoding.navigation.databinding.FragmentMainBinding

class MainFragment : Fragment() {

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    _binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnNavigation.setOnClickListener(
      Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_homeNavigationFragment)
    )
    binding.btnActionbar.setOnClickListener {
      Toast.makeText(activity, "ini action bar",Toast.LENGTH_SHORT).show()
      val intent = Intent(activity, ActionBarMainActivity::class.java)
      startActivity(intent)

//      view.findNavController().navigate(R.id.action_homeNavigationFragment_to_profileActivity)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}