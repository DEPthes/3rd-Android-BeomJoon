package com.example.app.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.data.local.PhotoDaoEntity
import com.example.app.databinding.ItemBookmarkBinding

class BookmarkRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bookmarkList = listOf<PhotoDaoEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookmarkHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookmarkRvAdapter.BookmarkHolder) {
            val item = bookmarkList[position]
            holder.bind(item)
        }
    }

    inner class BookmarkHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoDaoEntity) {
            Glide.with(binding.ivBookmarkPhoto.context)
                .load(item.thumb)
                .into(binding.ivBookmarkPhoto)

            itemView.setOnClickListener {
                itemClickListener?.onItemClick(item.photoId)
            }
        }
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setData(list: List<PhotoDaoEntity>) {
        bookmarkList = list
        notifyDataSetChanged()
    }
}