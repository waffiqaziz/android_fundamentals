package com.dicoding.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.local.entity.FavoriteEntity
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.dicoding.githubuser.helper.FavoriteDiffCallback
import com.dicoding.githubuser.ui.activity.DetailUserActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

  private val listFav = ArrayList<FavoriteEntity>()
  fun setListFav(listFav: List<FavoriteEntity>) {
    val diffCallback = FavoriteDiffCallback(this.listFav, listFav)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    this.listFav.clear()
    this.listFav.addAll(listFav)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
    val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return FavViewHolder(binding)
  }

  override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
    holder.bind(listFav[position])
  }

  inner class FavViewHolder(private val binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(fav: FavoriteEntity) {
      with(binding) {
        tvItemName.text = fav.login
        Glide.with(imgItemAvatar)
          .load(fav.avatar_url) // URL Avatar
          .circleCrop() // change avatar to circle
          .into(imgItemAvatar)

        imgItemAvatar.setOnClickListener {
          val intent = Intent(it.context, DetailUserActivity::class.java)
          intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, fav)
          it.context.startActivity(intent)
        }
      }
    }
  }

  override fun getItemCount() =  listFav.size
}