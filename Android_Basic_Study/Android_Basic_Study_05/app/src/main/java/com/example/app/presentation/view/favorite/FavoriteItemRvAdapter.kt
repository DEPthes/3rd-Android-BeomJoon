package com.example.app.presentation.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.data.model.Item
import com.example.app.databinding.ItemFavoriteListBinding

class FavoriteItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = ArrayList<Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavoriteListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            holder.bind(itemList[position])
        }
    }

    fun setData(list: ArrayList<Item>) {
        itemList = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val binding: ItemFavoriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .override(60,60)
                .into(binding.ivFavoriteThumbnail)

            binding.tvFavoriteItemName.text = item.title
            binding.tvFavoriteItemPrice.text = item.price.toString()
        }
    }
}