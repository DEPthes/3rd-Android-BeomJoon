package com.example.app.presentation.view.favorite

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.data.db.AppDatabase
import com.example.app.data.db.FavoriteItemEntity
import com.example.app.databinding.ItemFavoriteListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var favoriteItemList = mutableListOf<FavoriteItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavoriteListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteItemHolder) {
            holder.bind(favoriteItemList[position])
        }
    }

    fun setData(list: List<FavoriteItemEntity>) {
        favoriteItemList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class FavoriteItemHolder(val binding: ItemFavoriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context // 컨텍스트 가져오기

        fun bind(item: FavoriteItemEntity) {
            Glide.with(binding.root.context)
                .load(item.thumbnail)
                .override(60, 60)
                .into(binding.ivFavoriteThumbnail)

            binding.tvFavoriteItemName.text = item.title
            binding.tvFavoriteItemPrice.text = item.price.toString()

            binding.btnFavoriteDelete.setOnClickListener {
                deleteFavorites(item.id!!, context, adapterPosition)
            }
        }
    }

    private fun deleteFavorites(id: Int, context: Context, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).getFavoriteDAO()
                .deleteItem(id)
        }
        favoriteItemList.removeAt(position)
        Toast.makeText(context, "찜 목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
        notifyItemRemoved(position)
    }
}