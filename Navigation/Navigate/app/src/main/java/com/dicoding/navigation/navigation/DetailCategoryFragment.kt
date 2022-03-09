package com.dicoding.navigation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.dicoding.navigation.R
import com.dicoding.navigation.databinding.FragmentDetailCategoryBinding

class DetailCategoryFragment : Fragment() {

  private var _binding: FragmentDetailCategoryBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?){
    super.onViewCreated(view,savedInstanceState)

//    val dataName = arguments?.getString(CategoryFragment.EXTRA_NAME)
//    val dataDescription = arguments?.getLong(CategoryFragment.EXTRA_STOCK)

    val dataName = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
    val dataDescription = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stock

    binding.tvCategoryName.text = dataName
    binding.tvCategoryDescription.text = "Stock : $dataDescription"

    binding.btnProfile.setOnClickListener(
      Navigation.createNavigateOnClickListener(R.id.action_detailCategoryFragment_to_mainFragment)
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

}