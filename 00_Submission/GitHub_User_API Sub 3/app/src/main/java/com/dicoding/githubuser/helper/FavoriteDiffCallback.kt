package com.dicoding.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubuser.data.local.entity.FavoriteEntity

class FavoriteDiffCallback(
  private val mOldFavList: List<FavoriteEntity>,
  private val mNewFavList: List<FavoriteEntity>
) : DiffUtil.Callback() {

  override fun getOldListSize() = mOldFavList.size

  override fun getNewListSize() = mNewFavList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
     mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldEmployee = mOldFavList[oldItemPosition]
    val newEmployee = mNewFavList[newItemPosition]
    return oldEmployee.id == newEmployee.id
  }
}