package com.dicoding.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) :
  RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
  private lateinit var onItemClickCallback: OnItemClickCallback
  private lateinit var binding: ItemRowUserBinding

  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ListViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    val (_, name, avatar, _, _, company) = listUser[position]
    Glide.with(holder.binding.imgItemAvatar.context)
      .load(avatar) // URL Avatar
      .circleCrop() // change avatar to circle
      .into(binding.imgItemAvatar)
    holder.binding.tvItemName.text = name
    holder.binding.tvItemCompany.text = company

    // onclick on image
    binding.imgItemAvatar.setOnClickListener {
      onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
    }
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: User)
  }

  override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}
