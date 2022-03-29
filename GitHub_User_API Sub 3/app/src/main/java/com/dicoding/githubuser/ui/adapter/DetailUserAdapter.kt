package com.dicoding.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.dicoding.githubuser.data.remote.response.UserResponse

class DetailUserAdapter(
  private val listUser:
  ArrayList<UserResponse>
) :
  RecyclerView.Adapter<DetailUserAdapter.ViewHolder>() {
  private lateinit var binding: ItemRowUserBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  inner class ViewHolder(private var binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(user: UserResponse) {
      Glide.with(binding.root.context)
        .load(user.avatarUrl) // URL Avatar
        .circleCrop() // change avatar to circle
        .into(binding.imgItemAvatar)
      binding.tvItemName.text = user.login
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(listUser[position])
  }

  override fun getItemCount() = listUser.size
}