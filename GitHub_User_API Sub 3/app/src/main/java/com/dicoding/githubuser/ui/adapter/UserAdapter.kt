package com.dicoding.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class UserAdapter(
  private val listUser:
  ArrayList<ItemsItem>,
  private val listener: OnItemClickCallback
) :
  RecyclerView.Adapter<UserAdapter.ViewHolder>() {
  private lateinit var binding: ItemRowUserBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  class ViewHolder(private var binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root){
    fun onBind(user: ItemsItem, listener: OnItemClickCallback){
      Glide.with(binding.root.context)
        .load(user.avatarUrl) // URL Avatar
        .circleCrop() // change avatar to circle
        .into(binding.imgItemAvatar)
      binding.tvItemName.text = user.login

      // avatar OnClickListener
      binding.imgItemAvatar.setOnClickListener {
        listener.onItemClicked(user)
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