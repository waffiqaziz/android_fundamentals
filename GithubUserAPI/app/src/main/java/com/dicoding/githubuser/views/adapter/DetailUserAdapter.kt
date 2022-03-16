package com.dicoding.githubuser.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.UserResponse
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class DetailUserAdapter(
  private val listUser:
  ArrayList<UserResponse>,
  private val listener: OnItemClickCallback
) :
  RecyclerView.Adapter<DetailUserAdapter.ViewHolder>() {
  private lateinit var binding: ItemRowUserBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  class ViewHolder(var binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root){
    fun onBind(user: UserResponse, listener: OnItemClickCallback){
      Glide.with(binding.root.context)
        .load(user.avatarUrl) // URL Avatar
        .circleCrop() // change avatar to circle
        .into(binding.imgItemAvatar)
      binding.tvItemName.text = user.login

      // avatar OnClickListener
      binding.imgItemAvatar.setOnClickListener {
//        listener.onItemClicked(user)
      }
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(listUser[position],listener)
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: ItemsItem)
  }

  override fun getItemCount() = listUser.size


}