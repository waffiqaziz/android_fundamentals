package com.dicoding.restaurantreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.restaurantreview.databinding.ItemReviewBinding

class ReviewAdapter(private val listReview: List<String>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

  private lateinit var binding: ItemReviewBinding

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    binding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position:Int){
    viewHolder.binding.tvItem.text = listReview[position]
  }
  
  override fun getItemCount() = listReview.size
  
  class ViewHolder(var binding: ItemReviewBinding) :
    RecyclerView.ViewHolder(binding.root)

}