package com.example.app.presentation.view.Item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.db.AppDatabase
import com.example.app.data.db.FavoriteItemEntity
import com.example.app.databinding.ItemListBinding
import com.example.app.domain.model.item.ItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = mutableListOf<ItemEntity>()

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

    fun setData(list: List<ItemEntity>) {
        itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context // 컨텍스트 가져오기
        fun bind(item: ItemEntity) {
            Glide.with(context)
                .load(item.thumbnail)
                .override(60, 60)
                .into(binding.ivThumbnail)
            binding.tvItemName2.text = context.getString(R.string.text_item_name, item.title)
            binding.tvItemPrice.text =
                context.getString(R.string.text_item_price, item.price.toString())

            binding.btnFavoriteAdd.setOnClickListener {
                addToFavorites(item, context) // 아이템 찜 목록에 추가
            }
        }
    }

    private fun addToFavorites(item: ItemEntity, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).getFavoriteDAO()
                .insertItem(FavoriteItemEntity(null, item.price, item.thumbnail, item.title))
        }
        Toast.makeText(context, "찜 목록에 추가되었습니다", Toast.LENGTH_SHORT).show()
    }
}