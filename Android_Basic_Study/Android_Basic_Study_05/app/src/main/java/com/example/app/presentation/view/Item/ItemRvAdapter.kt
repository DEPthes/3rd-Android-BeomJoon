package com.example.app.presentation.view.Item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.data.model.Item
import com.example.app.databinding.ItemListBinding

class ItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = ArrayList<Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListBinding.inflate(
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

    inner class ItemHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .override(60, 60)
                .into(binding.ivThumbnail)

            binding.tvItemName.text = item.title
            binding.tvItemPrice.text = item.price.toString()
        }
    }
}